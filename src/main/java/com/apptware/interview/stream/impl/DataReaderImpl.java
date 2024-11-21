package com.apptware.interview.stream.impl;

import com.apptware.interview.stream.DataReader;
import com.apptware.interview.stream.PaginationService;
import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class DataReaderImpl implements DataReader { // Renamed to remove suffix

  @Autowired 
  private PaginationService paginationService;

  @Override
  public Stream<String> fetchLimitedData(int limit) { // Corrected method name
    return fetchPaginatedDataAsStream().limit(limit);
  }

  @Override
  public Stream<String> fetchFullData() {
    return fetchPaginatedDataAsStream();
  }

  /**
   * Fetches paginated data as a stream.
   * This method handles pagination and logs the data fetching behavior.
   */
  private @Nonnull Stream<String> fetchPaginatedDataAsStream() {
    log.info("Fetching paginated data as stream.");

    int pageSize = 100; // Define a reasonable page size
    return Stream.iterate(1, page -> page + 1)
      .takeWhile(page -> !paginationService.getPaginatedData(page, pageSize).isEmpty())
      .flatMap(page -> {
        try {
          List<String> data = paginationService.getPaginatedData(page, pageSize);
          return data.stream();
        } catch (Exception e) {
          log.error("Error fetching data for page {}: {}", page, e.getMessage());
          return Stream.empty(); // Return an empty stream in case of an error
        }
      })
      .peek(item -> log.info("Fetched Item: {}", item));
  }
}