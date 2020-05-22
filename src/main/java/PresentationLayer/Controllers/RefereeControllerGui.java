package PresentationLayer.Controllers;

//import ServiceLayer.*;
//import PresentationLayer.ScreenController;
//import System.Exeptions.NoRefereePermissions;
//import System.Exeptions.NoSuchEventException;

import PresentationLayer.ScreenController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.http.ResponseEntity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class RefereeControllerGui extends ControllerGUI{


    @FXML
    Button homeBtn;

    Pane currPane;

    @FXML
    Text userInfo;

    @FXML
    Button logOutBtn;

    @FXML
    Text time1;

    @FXML
    Text date1;

    @FXML
    Text teamHomeName1;

    @FXML
    Text teamAwayName1;

    @FXML
    Text time2;

    @FXML
    Text date2;

    @FXML
    Text teamHomeName2;

    @FXML
    Text teamAwayName2;

    @FXML
    Text time3;

    @FXML
    Text date3;

    @FXML
    Text teamHomeName3;

    @FXML
    Text teamAwayName3;

    @FXML
    Text time4;

    @FXML
    Text date4;

    @FXML
    Text teamHomeName4;

    @FXML
    Text teamAwayName4;

    @FXML
    Text time5;

    @FXML
    Text date5;

    @FXML
    Text teamHomeName5;

    @FXML
    Text teamAwayName5;

    @FXML
    Pane info1;

    @FXML
    Pane info2;

    @FXML
    Pane info3;

    @FXML
    Pane info4;

    @FXML
    Pane info5;

    @FXML
    Text teamNameAway;

    @FXML
    Text teamNameHome;

    @FXML
    Pane rightPane;

    HashMap<Pane, String> gameInfo;

    @FXML
    Text score;

    @FXML

    Pane liveInfo;

    @FXML
    Button postEvent;

    @FXML
    VBox eventMenu;
    String url="http://localhost:8090/api/referee";
    @FXML
    public void initialize() {
        gameInfo = new HashMap<>();
        userInfo.setText(ScreenController.getInstance().userName);
        String name = username;
        List<String> myGames = getListRequest(url+"/getMyGames/"+name);
        if (myGames.size() > 0) {
            String str = myGames.get(0);
            String[] arrayInfo = str.split(",");
            changeText(info1, teamHomeName1, teamAwayName1, time1, date1, arrayInfo);
            gameInfo.put(info1, arrayInfo[4]);
        }
        if (myGames.size() > 1) {
            String str = myGames.get(1);
            String[] arrayInfo = str.split(",");
            changeText(info2, teamHomeName2, teamAwayName2, time2, date2, arrayInfo);
            gameInfo.put(info2, arrayInfo[4]);
        }

//
        if (myGames.size() > 2) {
            String str = myGames.get(2);
            String[] arrayInfo = str.split(",");
            changeText(info3, teamHomeName3, teamAwayName3, time3, date3, arrayInfo);
            gameInfo.put(info3, arrayInfo[4]);
        }
//
        if (myGames.size() > 3) {
            String str = myGames.get(3);
            String[] arrayInfo = str.split(",");
            changeText(info4, teamHomeName4, teamAwayName4, time4, date4, arrayInfo);
            gameInfo.put(info4, arrayInfo[4]);
        }
//
        if (myGames.size() > 4) {
            String str = myGames.get(4);
            String[] arrayInfo = str.split(",");
            changeText(info5, teamHomeName5, teamAwayName5, time5, date5, arrayInfo);
            gameInfo.put(info5, arrayInfo[4]);
        }
    }
//
    private void changeText(Pane info, Text teamHomeName, Text teamAwayName, Text time, Text date, String[] arrayInfo) {
        info.setVisible(true);
        teamHomeName.setText(arrayInfo[0]);
        teamAwayName.setText(arrayInfo[1]);
        time.setText(arrayInfo[2]);
        date.setText(arrayInfo[3]);
    }


    public void updateEvent(String type, String info) {
        updateEvents();
        if (type.equals("Score")) {
            String refereeName = username;
            ResponseEntity<String> result = getStringRequest(url+"/getScore/"+info+"/"+refereeName);
            score.setText(result.getBody());
        }
    }

    @FXML
    public void postEvent(Event event) {
        try {
            String str;
            Button btn = ((Button) event.getSource());
            if(btn.getText().equals("Add event")){
                str = "AddEvent.fxml";
            }
            else{
                str = "AddEventReport.fxml";
            }
            Stage stage = new Stage();
            ScreenController.getInstance().saveGameInfo(gameInfo.get(currPane), teamNameHome.getText(), teamNameAway.getText(), this);
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(str));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
        }
    }


//    @FXML
    public void onEnteredPane(Event event) {
        try {
            Pane info = ((Pane) event.getSource());
            if (info != currPane) {
                info.setStyle("-fx-background-radius:10 ; -fx-background-color: #2060E4");
                for (Node node : info.getChildren()) {
                    if (node instanceof Text) {
                        node.setStyle("-fx-fill:white");
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    @FXML
    public void onExitPane(Event event) {
        try {
            Pane info = ((Pane) event.getSource());
            if (info != currPane) {
                info.setStyle("-fx-background-radius:10 ; -fx-background-color: #F6F6F4");
                for (Node node : info.getChildren()) {
                    if (node instanceof Text) {
                        node.setStyle("-fx-fill: #444444");
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    @FXML
    public void showRightMenu(Event event) {

        if (currPane != null) {
            currPane.setStyle("-fx-background-radius:10 ; -fx-background-color: #F6F6F4");
            for (Node node : currPane.getChildren()) {
                if (node instanceof Text) {
                    if (node instanceof Text) {
                        node.setStyle("-fx-fill: #444444");
                    }
                }
            }
        }
        rightPane.setVisible(true);
        Pane info = ((Pane) event.getSource());
        currPane = info;
        int i = 0;
        info.setStyle("-fx-background-radius:10 ; -fx-background-color:#2060E4");
        for (Node node : info.getChildren()) {
            if (node instanceof Text) {
                node.setStyle("-fx-fill:white");
                if (i == 3) {
                    teamNameHome.setText(((Text) node).getText());
                }
                if (i == 4) {
                    teamNameAway.setText(((Text) node).getText());
                }
                i++;
            }
        }



        String refereeName = username;
        String gameId = gameInfo.get(info);
        ResponseEntity<String> r =getStringRequest(url+"/getScore/"+gameId+"/"+refereeName);
        score.setText(r.getBody());
        ResponseEntity<String> result1= getStringRequest(url+"/isGameLive/"+gameId+"/"+refereeName);
        if (result1.getBody().equals("true")) {
            liveInfo.setVisible(true);
        } else {
            liveInfo.setVisible(false);
        }
        updateEvents();

    }

    @FXML
    public void mouseIn(Event event) {
        Button btn = ((Button) event.getSource());
        event.getTarget();
        btn.setStyle("-fx-background-radius : 10;-fx-background-color :  #2060E4 ; -fx-text-fill : white ");
    }

    @FXML
    public void mouseOut(Event event) {
        Button btn = ((Button) event.getSource());
        event.getTarget();
        btn.setStyle("-fx-background-radius : 10;-fx-background-color :  white ; -fx-text-fill :  #444444 ");
    }

    @FXML
    public void mouseInL() {
        logOutBtn.setStyle("-fx-background-radius : 10;-fx-background-color :  #a60000 ; -fx-text-fill : white ");
    }

    @FXML
    public void mouseOutL() {
        logOutBtn.setStyle("-fx-background-radius : 10;-fx-background-color :  #A73A33 ; -fx-text-fill :  white ");
    }


    @FXML
    public void handleMouseClickedPass(Event event) {
        Button btn = ((Button) event.getSource());
        btn.setStyle("-fx-background-color: #000F64 ; -fx-background-radius:10; -fx-text-fill: white");
    }

    @FXML
    public void onHover(Event event) {
        Button btn = ((Button) event.getSource());
        btn.setStyle("-fx-background-color: #4179F0 ; -fx-background-radius:10; -fx-text-fill: white");

    }

    @FXML
    public void OnExit(Event event) {
        Button btn = ((Button) event.getSource());
        btn.setStyle("-fx-background-color: #2060E4 ; -fx-background-radius:10; -fx-text-fill: white");
    }

    public void updateEvents() {
        String type="";
        eventMenu.getChildren().removeAll(eventMenu.getChildren());
        Pane pane2 = new Pane();
        pane2.setPrefWidth(600);
        pane2.setPrefHeight(15);
        pane2.setStyle("-fx-background-color:  White ; -fx-background-radius: 10 ;");

        eventMenu.getChildren().addAll(pane2);
        List<String> events = getListRequest(url+"/getEvents/"+gameInfo.get(currPane)+"/"+ username);
        for (String str : events) {
            if (!(str.contains("Goal") || str.contains("Yellow") || str.contains("Red"))) {
                continue;
            }
            String[] output = str.split(",");
            String firstField;
            String secField;
            if (teamNameAway.getText().equals(output[3])) {
                firstField = output[1];
                secField = output[2];
            } else {
                firstField = output[2];
                secField = output[1];
            }
            Pane pane = new Pane();
            pane.setPrefWidth(600);
            pane.setPrefHeight(50);
            pane.setStyle("-fx-background-color:  #F6F6F4 ; -fx-background-radius: 10 ;");

            Text playerName = new Text(firstField);
            playerName.setLayoutX(136);
            playerName.setLayoutY(30);
            playerName.setFill(Color.web("#444444"));
            playerName.setStyle("-fx-font-size: 20px;-fx-font-family:Open Sans");
            ImageView image = null;

            try {

                if (output[0].contains("Goal")) {
                    image = new ImageView(new Image(new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\pictures\\goal.png")));

                    image.setFitWidth(14);
                    image.setFitHeight(20);
                    type="Goal";
                } else if (output[0].contains("Yellow")) {
                    image = new ImageView(new Image(new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\pictures\\yellowCard.png")));

                    image.setFitWidth(14);
                    image.setFitHeight(20);
                } else if (output[0].contains("Red")) {

                    image = new ImageView(new Image(new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\pictures\\redCard.png")));
                }
                image.setFitWidth(14);
                image.setFitHeight(20);
            }catch (Exception e){
                e.printStackTrace();
            }


            image.setLayoutX(314);
            image.setLayoutY(13);
            Text time = new Text(secField);
            time.setLayoutX(394);
            time.setLayoutY(30);
            time.setFill(Color.web("#444444"));
            time.setStyle("-fx-font-size: 20px;-fx-font-family:Open Sans");
            final Button temp = new Button("edit");
            temp.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        editEvent(gameInfo.get(currPane),output[0],output[1]/*time*/,output[2]/*player*/,output[3]/*team*/,output[4]);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            pane.getChildren().addAll(temp,playerName, image, time);

            pane2 = new Pane();
            pane2.setPrefWidth(600);
            pane2.setPrefHeight(15);
            pane2.setStyle("-fx-background-color:  White ; -fx-background-radius: 10 ;");


            eventMenu.getChildren().addAll(pane, pane2);
        }
    }

    private void editEvent( String gameId, String type,String time, String playerName, String team,String eventID) throws IOException {

        Stage stage = new Stage();


            ScreenController.getInstance().saveGameInfo(gameInfo.get(currPane), teamNameHome.getText(), teamNameAway.getText(), this);
            final Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("AddEvent.fxml"));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
            Text lblData = (Text) root.lookup("#addEventText");
            if (lblData!=null) lblData.setText("Edit event");



        Button b=(Button)root.lookup("#post");
        EventHandler<ActionEvent> old =b.getOnAction();
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HashMap<String,String> details=new HashMap<>();
                details.put("user_name",username);
                details.put("game",gameId);
                ComboBox<String> ss= ((ComboBox<String>) root.lookup("#comboEventBox"));
                String s= ((ComboBox<String>) root.lookup("#comboEventBox")).getValue().replace(" ", "");
                details.put("type",s);
                s= ((TextField) root.lookup("#playerNameText")).getText();
                details.put("playerName",s);
                String team1="";
                if (((RadioButton) root.lookup("#home")).isSelected()) {
                    team1 = teamNameHome.getText();
                } else if (((RadioButton) root.lookup("#away")).isSelected()) {
                    team1 = teamNameAway.getText();
                }
                details.put("team",team1);
                details.put("event_id",eventID);
                s= ((TextField) root.lookup("#timeEvent")).getText();
                details.put("min",s);

                final String status=postRequestHashMap(url+"/editEventAfterGame",details).getBody();
                if(status.equals("fail")){
                    showAlert("status");
                }else{
                    showAlert("success");
                    stage.close();
                    updateEvents();
                }
            }
        });
    }

    public void update(){}


}





