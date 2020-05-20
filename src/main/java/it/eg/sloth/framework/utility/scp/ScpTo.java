package it.eg.sloth.framework.utility.scp;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Gestistice il trasferimento file vis scp
 *
 * @author Enrico Grillini
 */
public class ScpTo {

    private ScpTo() {
        // NOP
    }

    /**
     * Trasferisce il file all'host remoto
     *
     * @param session
     * @param localFileName
     * @param remoteFile
     * @throws JSchException
     * @throws IOException
     */
    private static final void scpTo(Session session, String localFileName, String remoteFile) throws JSchException, IOException {
        FileInputStream fis = null;
        try {

            boolean ptimestamp = true;

            // exec 'scp -t rfile' remotely
            String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + remoteFile;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();

            checkAck(in);

            File localeFile = new File(localFileName);

            if (ptimestamp) {
                command = "T" + (localeFile.lastModified() / 1000) + " 0";
                // The access time should be sent here, but it is not accessible with JavaAPI ;-<
                command += (" " + (localeFile.lastModified() / 1000) + " 0\n");
                out.write(command.getBytes());
                out.flush();
                checkAck(in);
            }

            // send "C0644 filesize filename", where filename should not include '/'
            long filesize = localeFile.length();
            command = "C0644 " + filesize + " ";
            if (localFileName.lastIndexOf('/') > 0) {
                command += localFileName.substring(localFileName.lastIndexOf('/') + 1);
            } else {
                command += localFileName;
            }
            command += "\n";

            out.write(command.getBytes());
            out.flush();
            checkAck(in);

            // send a content of lfile
            fis = new FileInputStream(localFileName);
            IOUtils.copy(fis, out);
            out.write(new byte[]{0}, 0, 1);
            out.flush();
            checkAck(in);
            out.close();

            channel.disconnect();

        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    public static final void scpTo(String host, String user, String password, ScpFile scpFile) throws IOException, JSchException {
        scpTo(host, user, password, new ScpFile[]{scpFile});
    }

    public static final void scpTo(String host, String user, String password, ScpFile[] scpFiles) throws IOException, JSchException {

        FileInputStream fis = null;
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);

            // Trasferimento password via UserInfo.
            UserInfo ui = new MyUser(password);
            session.setUserInfo(ui);
            session.connect();

            for (ScpFile scpFile : scpFiles) {
                InputStream inputStream = null;
                FileOutputStream outputStream = null;
                File file = null;
                try {
                    inputStream = new ByteArrayInputStream(scpFile.getBuffer());

                    file = File.createTempFile("localtemp", "scp");
                    file.deleteOnExit();
                    outputStream = new FileOutputStream(file);
                    IOUtils.copy(inputStream, outputStream);

                    scpTo(session, file.getAbsolutePath(), scpFile.getRemoteFile());
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if (file != null) {
                        file.delete();
                    }

                    if (outputStream != null) {
                        outputStream.close();
                    }

                }
            }

            session.disconnect();

        } finally {
            if (fis != null) {
                fis.close();
            }
        }

    }

    /**
     * Trasferisce lo stream all'host remoto
     *
     * @param inputStream
     * @param host
     * @param user
     * @param password
     * @param remoteFile
     * @throws IOException
     * @throws JSchException
     */
    public static final void scpTo(InputStream inputStream, String host, String user, String password, String remoteFile) throws IOException, JSchException {
        FileOutputStream outputStream = null;
        File file = null;
        try {
            file = File.createTempFile("localtemp", "scp");
            file.deleteOnExit();
            outputStream = new FileOutputStream(file);
            IOUtils.copy(inputStream, outputStream);

            scpTo(file.getAbsolutePath(), host, user, password, remoteFile);

        } finally {
            if (file != null) {
                file.delete();
            }

            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    /**
     * Trasferisce il file all'host remoto
     *
     * @param localFile
     * @param host
     * @param user
     * @param password
     * @param remoteFile
     * @throws IOException
     * @throws JSchException
     */
    public static final void scpTo(String localFile, String host, String user, String password, String remoteFile) throws IOException, JSchException {
        FileInputStream fis = null;
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);

            // Trasferimento password via UserInfo.
            UserInfo ui = new MyUser(password);
            session.setUserInfo(ui);
            session.connect();

            scpTo(session, localFile, remoteFile);

            session.disconnect();

        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * Verifica se l'operazione è o meno terminata correttamente: 0 success, 1 for error, 2 for fatal error, -1 Undefined error
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static void checkAck(InputStream in) throws IOException {
        int b = in.read();

        // Undefined Error
        if (b == -1) {
            throw new RuntimeException("Undefined error");
        }

        if (b == 1 || b == 2) {
            StringBuilder sb = new StringBuilder();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            } while (c != '\n');
            if (b == 1) {
                // Error
                throw new RuntimeException(sb.toString());
            }
            if (b == 2) {
                // Fatal error
                throw new RuntimeException(sb.toString());
            }
        }
    }

}