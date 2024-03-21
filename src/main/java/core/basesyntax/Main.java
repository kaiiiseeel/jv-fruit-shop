package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.CsvFruitDataReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.StrategyServiceImpl;
import core.basesyntax.service.impl.strategy.BalanceOperation;
import core.basesyntax.service.impl.strategy.PurchaseOperation;
import core.basesyntax.service.impl.strategy.ReturnOperation;
import core.basesyntax.service.impl.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String fileFrom = "src\\resources\\file.csv";
    private static final String fileTo = "src\\resources\\report.csv";
    private static final Map<String, Integer> storage = new Storage().getStorageData();
    private static final CsvFruitDataReaderImpl dataReader = new CsvFruitDataReaderImpl();
    private static final ReportGenerator reportGenerator = new ReportGeneratorImpl();
    private static final FileWriterService fileWriterService = new FileWriterImpl();
    private static final Map<String, OperationHandler> operations = new HashMap<>();

    public static void main(String[] args) {
        operations.put("b", new BalanceOperation());
        operations.put("p", new PurchaseOperation());
        operations.put("r", new ReturnOperation());
        operations.put("s", new SupplyOperation());

        StrategyServiceImpl service = new StrategyServiceImpl(operations);
        List<FruitTransactionDto> fruitTransactionDtoList = dataReader.readData(fileFrom);
        service.processData(fruitTransactionDtoList, operations);
        List<String> report = reportGenerator.createReport(storage);
        fileWriterService.write(report, fileTo);

        System.out.println(report);
        System.out.println(Storage.fruitStorage);
    }
}
