create sequence seq_idutente;
create sequence Seq_Idprofilo;

-- Create Table AFORISMI
Create Table AFORISMI
      (IDAFORISMA NUMBER(38,0),
       AFORISMA VARCHAR2(1000),
       AUTORE VARCHAR2(100),
       DATAULTIMAPUBLICAZIONE DATE);

-- Create Table SEC_Dec_Funzioni
Create Table SEC_Dec_Funzioni
      (CODFUNZIONE VARCHAR2(100),
       DESCRIZIONEBREVE VARCHAR2(20),
       DESCRIZIONELUNGA VARCHAR2(100),
       POSIZIONE NUMBER(38,0),
       FLAGVALIDO VARCHAR2(1));

-- Create Table SEC_Dec_Menu
Create Table SEC_Dec_Menu
      (CODMENU VARCHAR2(100),
       DESCRIZIONEBREVE VARCHAR2(20),
       DESCRIZIONELUNGA VARCHAR2(100),
       POSIZIONE NUMBER(38,0),
       FLAGVALIDO VARCHAR2(1),
       CODTIPOVOCE VARCHAR2(10),
       LIVELLO NUMBER(38,0),
       IMGHTML VARCHAR2(100),
       LINK VARCHAR2(200));

-- Create Table SEC_Dec_Menuutente
Create Table SEC_Dec_Menuutente
      (CODMENUUTENTE VARCHAR2(100),
       DESCRIZIONEBREVE VARCHAR2(20),
       DESCRIZIONELUNGA VARCHAR2(100),
       POSIZIONE NUMBER(38,0),
       FLAGVALIDO VARCHAR2(1),
       CODTIPOVOCE VARCHAR2(10),
       IMGHTML VARCHAR2(100),
       LINK VARCHAR2(200));

-- Create Table SEC_Dec_Ruoli
Create Table SEC_Dec_Ruoli
      (CODRUOLO VARCHAR2(10),
       DESCRIZIONEBREVE VARCHAR2(20),
       DESCRIZIONELUNGA VARCHAR2(100),
       POSIZIONE NUMBER(38,0),
       FLAGVALIDO VARCHAR2(1));

-- Create Table SEC_Dec_Tipivoce
Create Table SEC_Dec_Tipivoce
      (CODTIPOVOCE VARCHAR2(10),
       DESCRIZIONEBREVE VARCHAR2(20),
       DESCRIZIONELUNGA VARCHAR2(100),
       POSIZIONE NUMBER(38,0),
       FLAGVALIDO VARCHAR2(1),
       FLAGMENUUTENTE VARCHAR2(1));

-- Create Table SEC_Funzioniruoli
Create Table SEC_Funzioniruoli
      (CODFUNZIONE VARCHAR2(100),
       CODRUOLO VARCHAR2(10),
       FLAGACCESSO VARCHAR2(1));

-- Create Table SEC_Menuruoli
Create Table SEC_Menuruoli
      (CODMENU VARCHAR2(100),
       CODRUOLO VARCHAR2(10),
       FLAGACCESSO VARCHAR2(1));

-- Create Table SEC_Menuutenteruoli
Create Table SEC_Menuutenteruoli
      (CODMENUUTENTE VARCHAR2(100),
       CODRUOLO VARCHAR2(10),
       FLAGACCESSO VARCHAR2(1));

-- Create Table SEC_Profili
Create Table SEC_Profili
      (IDPROFILO NUMBER(38,0),
       IDUTENTE NUMBER(38,0),
       CODRUOLO VARCHAR2(10),
       DATAINIZIO DATE,
       DATAFINE DATE);

-- Create Table SEC_Utenti
Create Table SEC_Utenti
      (IDUTENTE NUMBER(38,0),
       NOME VARCHAR2(100),
       COGNOME VARCHAR2(100),
       USERID VARCHAR2(10),
       PASSWORD VARCHAR2(10),
       EMAIL VARCHAR2(100),
       EMAILPASSWORD VARCHAR2(100),
       LOCALE VARCHAR2(5),
       FOTO BLOB);

-- Create Index AFORISMI_PK
Create Unique Index AFORISMI_PK on AFORISMI (IDAFORISMA);

-- Create Index SEC_DEC_FUNZIONI_PK
Create Unique Index SEC_DEC_FUNZIONI_PK on SEC_Dec_Funzioni (CODFUNZIONE);

-- Create Index SEC_DEC_MENU_PK
Create Unique Index SEC_DEC_MENU_PK on SEC_Dec_Menu (CODMENU);

-- Create Index SEC_DEC_MENUUTENTE_PK
Create Unique Index SEC_DEC_MENUUTENTE_PK on SEC_Dec_Menuutente (CODMENUUTENTE);

-- Create Index SEC_DEC_RUOLI_PK
Create Unique Index SEC_DEC_RUOLI_PK on SEC_Dec_Ruoli (CODRUOLO);

-- Create Index SEC_DEC_TIPIVOCE_PK
Create Unique Index SEC_DEC_TIPIVOCE_PK on SEC_Dec_Tipivoce (CODTIPOVOCE);

-- Create Index SEC_FUNZIONIRUOLI_IDX1
Create Index SEC_FUNZIONIRUOLI_IDX1 on SEC_Funzioniruoli (CODRUOLO);

-- Create Index SEC_FUNZIONIRUOLI_IDX2
Create Index SEC_FUNZIONIRUOLI_IDX2 on SEC_Funzioniruoli (CODFUNZIONE);

-- Create Index SEC_FUNZIONIRUOLI_PK
Create Unique Index SEC_FUNZIONIRUOLI_PK on SEC_Funzioniruoli (CODFUNZIONE,CODRUOLO);

-- Create Index SEC_MENURUOLI_IDX1
Create Index SEC_MENURUOLI_IDX1 on SEC_Menuruoli (CODRUOLO);

-- Create Index SEC_MENURUOLI_IDX2
Create Index SEC_MENURUOLI_IDX2 on SEC_Menuruoli (CODMENU);

-- Create Index SEC_MENURUOLI_PK
Create Unique Index SEC_MENURUOLI_PK on SEC_Menuruoli (CODMENU,CODRUOLO);

-- Create Index SEC_MENUUTENTERUOLI_IDX1
Create Index SEC_MENUUTENTERUOLI_IDX1 on SEC_Menuutenteruoli (CODRUOLO);

-- Create Index SEC_MENUUTENTERUOLI_IDX2
Create Index SEC_MENUUTENTERUOLI_IDX2 on SEC_Menuutenteruoli (CODMENUUTENTE);

-- Create Index SEC_MENUUTENTERUOLI_PK
Create Unique Index SEC_MENUUTENTERUOLI_PK on SEC_Menuutenteruoli (CODMENUUTENTE,CODRUOLO);

-- Create Index SEC_PROFILI_PK
Create Unique Index SEC_PROFILI_PK on SEC_Profili (IDPROFILO);

-- Create Index SEC_UTENTI_PK
Create Unique Index SEC_UTENTI_PK on SEC_Utenti (IDUTENTE);

