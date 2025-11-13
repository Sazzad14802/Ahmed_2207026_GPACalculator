package com.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class homeScreenController {

    @FXML
    private void switchToCalcScreen() throws IOException {
        App.setRoot("calcScreen");
    }
}
