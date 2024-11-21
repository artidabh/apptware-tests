package com.apptware.interview.stream;

import static com.apptware.interview.stream.PaginationService.FULL_DATA_SIZE;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ConsumptionOnDemandTest {

  @Autowired private DataReader dataReader;

  @Test
  void testConsumptionOnDemand() {
    int limit = 1000;
    List<String> limitedData = dataReader.fetchLimitedData(limit).toList();
    Assertions.assertThat(limitedData).hasSize(limit);

    List<String> fullData = dataReader.fetchFullData().toList();
    Assertions.assertThat(fullData).hasSize(FULL_DATA_SIZE);
  }
}