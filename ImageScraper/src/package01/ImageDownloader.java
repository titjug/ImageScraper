package package01;
import java.io.*;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageDownloader {
    private static boolean isDownloading = false;
    private static JButton startButton;
    private static JButton stopButton;

    public static void main(String[] args) {
        // create the window
        JFrame frame = new JFrame("Image Downloader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 100);

        // create the start button
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // disable the start button and enable the stop button
                startButton.setEnabled(false);
                stopButton.setEnabled(true);

                // start downloading images
                isDownloading = true;
                downloadImages();
            }
        });

        // create the stop button
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // disable the stop button and enable the start button
                stopButton.setEnabled(false);
                startButton.setEnabled(true);

                // stop downloading images
                isDownloading = false;
            }
        });

        // add the buttons to the window
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        frame.getContentPane().add(buttonPanel);

        // show the window
        frame.setVisible(true);
    }

    private static void downloadImages() {
        // specify the website to download images from
        String websiteUrl = "https://example.com/";

        // specify the folder path to save the downloaded images
        String saveFolder = "C:\\Users\\Gaming PC\\Desktop\\images\\";

        try {
            // connect to the website and get the HTML document
            Document doc = Jsoup.connect(websiteUrl).get();

            // find all the image elements in the HTML document
            Elements imgElements = doc.getElementsByTag("img");

            // iterate through the image elements and download each image
            for (Element img : imgElements) {
                if (!isDownloading) {
                    break;
                }

                // get the source URL of the image
                String imgUrl = img.absUrl("src");

                // create a file object for the downloaded image
                File imageFile = new File(saveFolder + imgUrl.substring(imgUrl.lastIndexOf("/") + 1));

                // download the image and save it to the file
                URL url = new URL(imgUrl);
                InputStream is = url.openStream();
                OutputStream os = new FileOutputStream(imageFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) != -1) {
                    os.write(buffer, 0, length);
                }
                is.close();
                os.close();
            }

            System.out.println("All images downloaded successfully.");

        } catch (IOException e) {
            System.out.println("Error while downloading images: " + e.getMessage());
        }

        // re-enable the start button and disable the stop button
        isDownloading = false;
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
    }
}

