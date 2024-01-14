

package application.controllers;

import application.entities.Supplement;
import application.ui.AlertUtil;
import application.ui.SwitchScene;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import java.net.URI;

public class SupplementController implements Initializable {

    @FXML
    private Button coachesButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button membersButton;

    @FXML
    private Button membershipsButton;

    @FXML
    private BorderPane rootPane;

    @FXML
    private Button supplementsButton;

    private final String URL = "https://www.ogistra-nutrition-shop.com/2-pocetak";
    private HostServices hostServices;

    @FXML
    void logOut(ActionEvent event) throws IOException {
        boolean isConfirmed = AlertUtil.showConfirm("Confirmation message",
                "Are you sure you want to log out?");
        if (isConfirmed) SwitchScene.change("Log in", "main-view.fxml", event);
    }

    @FXML
    void switchToCoaches(ActionEvent event) throws IOException {
        SwitchScene.change("Coach", "coach-view.fxml", event);
    }

    @FXML
    void switchToDashboard(ActionEvent event) throws IOException {
        SwitchScene.change("Dashboard", "dashboard-view.fxml", event);
    }

    @FXML
    void switchToMembers(ActionEvent event) throws IOException {
        SwitchScene.change("Members", "member-view.fxml", event);
    }

    @FXML
    void switchToMemberships(ActionEvent event) throws IOException {
        SwitchScene.change("Membership", "membership-view.fxml", event);
    }

    private List<Supplement> scrapeSupplements(String url) {
        List<Supplement> supplementList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements supplementElements = document.
                    select(".item-product");
            for (Element element : supplementElements) {
                String imageUrl = element.
                        select(".thumbnail.product-thumbnail img.first-image")
                        .attr("src");
                String supplementName = element.select("a.product_name").text();
                String supplementUrl = element.select("a.product_name").attr("href");
                String price = element.select("span.price").text();

                supplementList.add(new Supplement(imageUrl, supplementName, price, supplementUrl));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return supplementList;
    }

    private VBox createProductCard(Supplement supplement) {
        VBox card = new VBox();
        card.setSpacing(3);
        card.setPadding(new Insets(15,15,15,15));
        card.setAlignment(Pos.CENTER);

        card.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        card.setEffect(new DropShadow(10, Color.GRAY));

        ImageView imageView = new ImageView(new Image(supplement.getImageUrl()));
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        Label productNameText = new Label(supplement.getSupplementName());
        Label priceText = new Label(supplement.getPrice());
        priceText.setStyle("-fx-font-weight: bold");

        Button buyButton = getBuyButton(supplement);

        card.getChildren().addAll(imageView, productNameText, priceText, buyButton);

        return card;
    }

    private static Button getBuyButton(Supplement supplement) {
        Button buyButton = new Button("Buy");
        buyButton.setOnAction(e -> {
            String url = supplement.getSupplementUrl();
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(url));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        return buyButton;
    }

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(25, 25, 25, 25));
        flowPane.setVgap(20);
        flowPane.setHgap(20);

        List<Supplement> supplementList = scrapeSupplements(URL);

        // Create JavaFX card components for each product
        for (Supplement supplement : supplementList) {
            VBox supplementCard = createProductCard(supplement);
            flowPane.getChildren().add(supplementCard);
        }

        ScrollPane scrollPane = new ScrollPane(flowPane);
        scrollPane.setFitToWidth(true);
        rootPane.setCenter(scrollPane);
    }
}
