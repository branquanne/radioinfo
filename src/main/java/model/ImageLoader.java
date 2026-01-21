package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

public class ImageLoader {
  public ImageIcon loadAndScaleImage(String url, int width, int height) {
    if (url == null || url.isBlank()) {
      return null;
    }
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width/Heigh must be > 0");
    }
    try {
      BufferedImage image = ImageIO.read(URI.create(url).toURL());
      BufferedImage scaledImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = scaledImage.createGraphics();
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g.drawImage(image.getScaledInstance(64, 64, Image.SCALE_SMOOTH), 0, 0, null);
      g.dispose();
      return new ImageIcon(scaledImage);

    } catch (MalformedURLException e) {
      throw new RuntimeException("Bad image URL");
    } catch (IOException e) {
      throw new RuntimeException("Failed to load image");
    }
  }
}
