package it.eg.sloth;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.db.decodemap.map.BaseDecodeMap;
import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.chart.SimpleChart;
import it.eg.sloth.form.chart.element.Labels;
import it.eg.sloth.form.chart.element.Series;
import it.eg.sloth.form.fields.field.impl.ComboBox;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.form.fields.field.impl.TextTotalizer;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.security.Menu;
import it.eg.sloth.framework.security.VoiceType;
import it.eg.sloth.jaxb.form.ChartType;
import it.eg.sloth.jaxb.form.LegendPosition;
import it.eg.sloth.webdesktop.search.SearchManager;
import it.eg.sloth.webdesktop.search.SearchRelevance;
import it.eg.sloth.webdesktop.search.impl.DataTableSearcher;
import it.eg.sloth.webdesktop.search.impl.MenuSearcher;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.Map;

import static org.mockito.Mockito.when;

public class TestFactory {

    public static final String OUTPUT_DIR = "target/test-output";

    public static void createOutputDir() throws IOException {
        Files.createDirectories(Paths.get(OUTPUT_DIR));
    }

    public static Grid getGrid() throws FrameworkException {
        Table table = new Table();
        Row row = table.add();
        row.setString("campo1", "valore1");
        row.setString("campo2", "A");
        row.setTimestamp("campo3", TimeStampUtil.parseTimestamp("01/01/2020", TimeStampUtil.DEFAULT_FORMAT));
        row.setBigDecimal("campo4", BigDecimal.valueOf(3.333));
        row.setString("campo5", "U");

        row = table.add();
        row.setString("campo1", "valore2");
        row.setString("campo2", "B");
        row.setTimestamp("campo3", TimeStampUtil.parseTimestamp("01/01/2020", TimeStampUtil.DEFAULT_FORMAT));
        row.setBigDecimal("campo4", BigDecimal.valueOf(-3.444));
        row.setString("campo5", "B");

        row = table.add();
        row.setString("campo1", "valore2");
        row.setString("campo2", "B");
        row.setTimestamp("campo3", TimeStampUtil.parseTimestamp("01/02/2020", TimeStampUtil.DEFAULT_FORMAT));
        row.setBigDecimal("campo4", BigDecimal.valueOf(-1));
        row.setString("campo5", "A");

        Grid result = new Grid<>("provaGrid", null);
        result.setTitle("Prova Grid");
        result.setDescription("Prova sottotitolo");
        result.addChild(new Text<String>("campo1", "campo 1", DataTypes.STRING));
        result.addChild(new Text<String>("campo2", "campo 2", DataTypes.STRING));
        result.addChild(new Text<Timestamp>("campo3", "campo 3", DataTypes.MONTH));
        result.addChild(new TextTotalizer("campo4", "campo 4", DataTypes.CURRENCY));
        result.addChild(new ComboBox<String>("campo5", "campo 5", DataTypes.STRING));

        ComboBox<String> comboBox = (ComboBox<String>) result.getElement("campo5");
        comboBox.setDecodeMap(new StringDecodeMap("A,Ancora;B,Basta;U,Un po'"));

        result.setDataSource(table);

        result.setLocale(Locale.ITALY);

        return result;
    }

    public static SimpleChart<DataTable<?>> getSimpleChart(ChartType chartType) throws FrameworkException {
        SimpleChart<DataTable<?>> result = new SimpleChart<>("Prova", chartType, "Prova", LegendPosition.TOP);

        result.addChild(new Labels<Timestamp>("Ora", "Ora", DataTypes.MONTH));
        result.addChild(new Series("Excecutions", "Excecutions", DataTypes.INTEGER));

        DataTable<?> table = new Table();
        DataRow row = table.add();
        row.setTimestamp("Ora", TimeStampUtil.parseTimestamp("01/01/2020"));
        row.setBigDecimal("Excecutions", BigDecimal.valueOf(25));

        row = table.add();
        row.setTimestamp("Ora", TimeStampUtil.parseTimestamp("01/02/2020"));
        row.setBigDecimal("Excecutions", BigDecimal.valueOf(15));

        row = table.add();
        row.setTimestamp("Ora", TimeStampUtil.parseTimestamp("01/03/2020"));
        row.setBigDecimal("Excecutions", BigDecimal.valueOf(35));

        result.setDataTable(table);

        return result;
    }

    public static SearchManager getSearchManager() {
        SearchManager result = new SearchManager();

        // MenuSearcher
        Menu menu = new Menu()
                .addChild("CLI", VoiceType.VOICE, "Clienti", "Gestione clienti", "immagine", "#")
                .addChild("ORD", VoiceType.VOICE, "Ordini", "Gestione ordini", "immagine", "#")
                .addChild("OFF", VoiceType.VOICE, "Offerte", "Gestione offerte", "immagine", "#")
                .addChild("DEC", VoiceType.VOICE, "Decodifiche", "Decodifiche", null, null);

        menu.getChilds().get("DEC").addChild("TIP", VoiceType.VOICE, "Tipo Clienti", "Tipo Clienti", null, "#");

        result.addSearcher(new MenuSearcher(menu), SearchRelevance.VERY_HIGH);

        // DataTableSearcher 1
        Table table1 = new Table();
        Row row1 = table1.add();
        row1.setBigDecimal("Id", BigDecimal.valueOf(1));
        row1.setString("value", "Bob");
        row1.setString("subValue", "Bob the Builder - Tipo costrutture");

        row1 = table1.add();
        row1.setBigDecimal("Id", BigDecimal.valueOf(2));
        row1.setString("value", "Alice");
        row1.setString("subValue", "Alice in wonderland - Tipo manager");

        DataTableSearcher<Row> dataTableSearcher1 = new DataTableSearcher<>("Clienti", "id", "value", "subValue", "prova.html?id=");
        dataTableSearcher1.setKeyWords("Clienti", "Cliente", "Cli");
        dataTableSearcher1.addData(table1);

        result.addSearcher(dataTableSearcher1, SearchRelevance.MEDIUM);

        // DataTableSearcher 2
        Table table2 = new Table();
        Row row2 = table2.add();
        row2.setBigDecimal("Id", BigDecimal.valueOf(1));
        row2.setString("value", "Ordine cliente Bob");
        row2.setString("subValue", "5 prodotti - 1.000€");

        row2 = table2.add();
        row2.setBigDecimal("Id", BigDecimal.valueOf(2));
        row2.setString("value", "Ordine cliente Alice");
        row2.setString("subValue", "4 prodotti - 500€");

        DataTableSearcher<Row> dataTableSearcher2 = new DataTableSearcher<>("Ordini", "id", "value", "subValue", "prova.html?id=");
        dataTableSearcher2.setKeyWords("Ordini", "Ordine", "Ord");
        dataTableSearcher2.addData(table2);

        result.addSearcher(dataTableSearcher2, SearchRelevance.MEDIUM);

        return result;
    }

    public static BaseDecodeMap<BigDecimal> getBaseDecodeMap() {
        BaseDecodeMap<BigDecimal> result = new BaseDecodeMap<>();
        result.put(BigDecimal.valueOf(1), "Valore A");
        result.put(BigDecimal.valueOf(2), "Valore B");
        result.put(BigDecimal.valueOf(3), "Valore C");
        result.put(BigDecimal.valueOf(4), "Valore D");

        return result;
    }


    public static WebRequest getMockedWebRequest(Map<String, String[]> map) throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getParameterMap()).thenReturn(map);

        return new WebRequest(request);
    }


}
