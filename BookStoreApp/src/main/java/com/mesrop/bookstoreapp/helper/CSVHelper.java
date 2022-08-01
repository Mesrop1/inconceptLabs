package com.mesrop.bookstoreapp.helper;

import com.mesrop.bookstoreapp.persistance.entity.*;
import com.mesrop.bookstoreapp.persistance.repository.BookRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CSVHelper {

    private final BookRepository repository;

    public CSVHelper(BookRepository repository) {
        this.repository = repository;
    }

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public List<BookEntity> parseBooks(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));) {
            CSVParser csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT
                            .withNullString("NULL")
                            .withQuote(null)
                            .withFirstRecordAsHeader()
                            .withIgnoreHeaderCase()
                            .withTrim()
                            .withDelimiter(';'));
            fileReader.readLine();
            List<BookEntity> books = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
               // String isbn = csvRecord.get("\"ISBN\"").trim();
                BookEntity bookEntity = new BookEntity();
                AuthorEntity author = new AuthorEntity();
                PublisherEntity publisher = new PublisherEntity();
                author.setName(csvRecord.get("\"Book-Author\"").replaceAll("\"", "").trim());
                bookEntity.setAuthor(author);
                publisher.setName(csvRecord.get("\"Publisher\"").replaceAll("\"", "").trim());
                bookEntity.setPublisher(publisher);
                bookEntity.setIsbn(csvRecord.get("\"ISBN\"").replaceAll("\"", "").trim());
//                if(repository.existsByIsbn(isbn)){
//                    continue;
//                }
                bookEntity.setTitle(csvRecord.get("\"Book-Title\"").replaceAll("\"", "").trim());
                if (csvRecord.get("\"Book-Title\"").length() > 255) {
                    continue;
                }
                bookEntity.setYear(csvRecord.get("\"Year-Of-Publication\"").replaceAll("\"", "").trim());
                bookEntity.setSmallImage(csvRecord.get("\"Image-URL-S\"").replaceAll("\"", "").trim());
                bookEntity.setMediumImage(csvRecord.get("\"Image-URL-M\"").replaceAll("\"", "").trim());
                bookEntity.setLargeImage(csvRecord.get("\"Image-URL-L\"").replaceAll("\"", "").trim());
                books.add(bookEntity);
            }
            return books;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static List<UserEntity> parseUsers(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));) {
            CSVParser csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT
                            .withNullString("NULL")
                            .withQuote(null)
                            .withFirstRecordAsHeader()
                            .withIgnoreHeaderCase()
                            .withTrim()
                            .withIgnoreSurroundingSpaces()
                            .withDelimiter(';'));
            fileReader.readLine();
            List<UserEntity> users = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                AddressEntity addressEntity = new AddressEntity();
                UserEntity userEntity = new UserEntity();
                //userEntity.setBusinessId(Long.parseLong(csvRecord.get("\"User-ID\"").replaceAll("\"", "").trim()));
                List<String> location = Arrays.asList(csvRecord.get("\"Location\"").trim().split("\\s*,\\s*"));
                addressEntity.setCity(location.get(0));
                userEntity.setAddress(addressEntity);
                users.add(userEntity);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static List<RatingEntity> parseRatings(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));) {
            CSVParser csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT
                            .withQuote(null)
                            .withFirstRecordAsHeader()
                            .withIgnoreHeaderCase()
                            .withTrim()
                            .withRecordSeparator("\r\n")
                            .withIgnoreEmptyLines(true)
                            .withDelimiter(';'));
            fileReader.readLine();
            List<RatingEntity> ratings = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                RatingEntity ratingEntity = new RatingEntity();
                ratingEntity.setRating(csvRecord.get("\"Book-Rating\"").replaceAll("\"", "").trim());
                ratings.add(ratingEntity);
            }
            return ratings;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
