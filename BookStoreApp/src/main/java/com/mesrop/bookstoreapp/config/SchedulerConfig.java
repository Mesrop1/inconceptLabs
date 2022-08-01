package com.mesrop.bookstoreapp.config;

import com.mesrop.bookstoreapp.enums.Status;
import com.mesrop.bookstoreapp.helper.Constants;
import com.mesrop.bookstoreapp.persistance.entity.BookEntity;
import com.mesrop.bookstoreapp.persistance.repository.BookRepository;
import org.imgscalr.Scalr;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private final BookRepository bookRepository;

    public SchedulerConfig(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

  @Scheduled(cron = "0/5 * * * * ?")
    private void download() {
        List<BookEntity> books = bookRepository.findByLargeImage();
        for (int i = 0; i < books.size(); i++) {
            BookEntity book = books.get(i);
            String largeImageUrl = book.getLargeImage();
            try (BufferedInputStream in = new BufferedInputStream(
                    new URL(largeImageUrl.trim().replaceAll("\"", "")).openStream())) {
                File file = new File(Constants.large_image + i + ".jpg");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
               BufferedImage image = ImageIO.write(file);
//                image = resize(image, 500);
//                saveThumbnail(file, image);
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    book.setStatus(Status.DOWNLOADING);
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    ImageIO.write(Scalr.resize(image, 150), "jpg", new File(Constants));
                }
                book.setFilePath(Constants.large_image + i + ".jpg");
                book.setSmallImageLocalPath(Constants.resized_image + i + ".jpg");
                book.setStatus(Status.FINISHED);
                bookRepository.save(book);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    private void resizeImage() throws IOException {
//        int width = 140;
//        int height = 140;
//        File input = new File(Constants.large_image);
//        BufferedImage image = ImageIO.read(input);
//        BufferedImage resized = resize(image, width, height);
//        File output = new File(Constants.resized_image);
//        ImageIO.write(resized, "png", output);
//    }
//
//    public static BufferedImage resize(BufferedImage image, int width, int height) {
//        Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2d = resized.createGraphics();
//        g2d.drawImage(tmp, 0, 0, null);
//        g2d.dispose();
//        return resized;
//    }
//
//    BufferedImage resizeImage(BufferedImage originalImage) throws IOException {
//        int targetHeight = 140;
//        int targetWidth = 140;
//        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
//        Graphics2D graphics2D = resizedImage.createGraphics();
//        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
//        graphics2D.dispose();
//        return resizedImage;
//    }

    private static void saveThumbnail(File originalFile, BufferedImage thumbnail)
            throws IOException {
        String filename = originalFile.getName();
        // Determine file extension.
        String fileExt = filename.substring(filename.lastIndexOf('.') + 1);
        // Save the thumbnail to the resized dir.
        ImageIO.write(thumbnail, fileExt, new File(Constants.resized_image));
    }
}
