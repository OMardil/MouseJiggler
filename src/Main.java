
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
public class Main extends Application
{
    int menuSize = 375;
    int menuSizeTwo = 60;
    int minimumSeconds = 0;
    int maximumSeconds = 60;
    int minimumMinutes = 0;
    int maximumMinutes = 60;
    int minimumHours = 0;
    int maximumHours = 24;
    Spinner<Integer> seconds = new Spinner<Integer>(minimumSeconds,maximumSeconds,0,1);
    Spinner<Integer> minutes = new Spinner<Integer>(minimumMinutes,maximumMinutes,0,1);
    Spinner<Integer> hours = new Spinner<Integer>(minimumHours,maximumHours,0,1);
    int delayamount = 1000;//how many ms between iterations?
    @Override
    public void start(Stage primaryStage)
    {
    	setupSpinners();
        VBox holder = new VBox();
        HBox inVBox = new HBox();
        Text message = new Text("You're inputting how many seconds to jiggle");
        Button btJiggle = new Button("Jiggle the mouse pls");//button to jiggle the mouse
        btJiggle.setOnAction(new jigglehandler());//wire the button to jiggle the mouse
        inVBox.getChildren().add(seconds);
        inVBox.getChildren().add(minutes);
        inVBox.getChildren().add(hours);
        inVBox.getChildren().add(btJiggle);//put the button and spinners next to each other
        holder.getChildren().add(message);
        holder.getChildren().add(inVBox);//then put the text and the group from earlier in the window
        Scene primscene = new Scene(holder,menuSize,menuSizeTwo);
        primaryStage.setScene(primscene);//make the scene with all the stuff in it and set it to the main window
        primaryStage.show();//show the main window
    }
	private void setupSpinners() {
    	seconds.setEditable(false);
    	minutes.setEditable(false);
    	hours.setEditable(false);
    	seconds.valueProperty().addListener(new secondsIncrementHandler());
    	minutes.valueProperty().addListener(new minutesIncrementHandler());
    	hours.valueProperty().addListener(new hoursIncrementHandler());
	}
    class jigglehandler implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {
            int howlongnum = 0;
            try
            {
                PointerInfo a = MouseInfo.getPointerInfo();
                Point b = a.getLocation();
                int x = (int) b.getX();
                int y = (int) b.getY();
                Robot bot = new Robot();
                howlongnum = parseInput();
                bot.setAutoWaitForIdle(true);
                for(int i = 0; i<howlongnum;i++)
                {
                    a = MouseInfo.getPointerInfo();
                    b = a.getLocation();
                    x = (int) b.getX();
                    y = (int) b.getY();//get the location of the mouse each time the loop starts
                    if(i%2==0)
                    {
                        bot.mouseMove(x+10,y-10);
                        bot.delay(delayamount);
                    }
                    else
                    {
                        bot.mouseMove(x-10,y+10);
                        bot.delay(delayamount);
                    }//Wait a second, then if it's an even cycle of movement, move it to the right, otherwise, move it to the left
                }
            }
            catch(NumberFormatException e)
            {
                Stage ErrorStage = new Stage();
                HBox inside = new HBox();
                Scene ErrorScene = new Scene(inside,menuSize,menuSizeTwo);
                Text error = new Text("There was a goof in how long you asked for, try again?");
                inside.getChildren().add(error);
                ErrorStage.setScene(ErrorScene);
                ErrorStage.show();
                //if they didn't type a number, tell them to type a number
            } catch (AWTException ex) {
                Stage ErrorStage = new Stage();
                HBox inside = new HBox();
                Scene ErrorScene = new Scene(inside,menuSize,menuSizeTwo);
                Text error = new Text("The Bot Is A N G E R");
                inside.getChildren().add(error);
                ErrorStage.setScene(ErrorScene);
                ErrorStage.show();
            }
        }

		private Integer parseInput() {
			return seconds.getValue();
		}
    
    }
    class secondsIncrementHandler implements ChangeListener<Integer>
    {

		@Override
		public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
			if(oldValue == maximumSeconds-1 && newValue == maximumSeconds)
			{
				minutes.increment(1);
				seconds.getValueFactory().setValue(minimumSeconds);
				//if they max out seconds, increment minutes, reset seconds
			}
		}
    	
    }
    class minutesIncrementHandler implements ChangeListener<Integer>
    {

		@Override
		public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
			if(oldValue == maximumMinutes-1 && newValue == maximumMinutes)
			{
				hours.increment(1);
				minutes.getValueFactory().setValue(minimumMinutes);
				//if they max out minutes, increment hours, reset minutes
			}
		}
    	
    }
    class hoursIncrementHandler implements ChangeListener<Integer>
    {

		@Override
		public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
			if(oldValue == maximumHours-1 && newValue == maximumHours)
			{
				seconds.getValueFactory().setValue(minimumSeconds);
				minutes.getValueFactory().setValue(minimumMinutes);
				hours.getValueFactory().setValue(minimumHours);
				//if they max out hours, it resets the whole schpiel
			}
		}
    	
    }
    public void typeThings(String phrase) 
    {
        try
        {
            Robot robot = new Robot();    
            for (int i = 0; i < phrase.length(); i++) {
                char c = phrase.charAt(i);
                if (Character.isUpperCase(c)) 
                {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                }
                robot.keyPress(Character.toUpperCase(c));
                robot.keyRelease(Character.toUpperCase(c));
                if (Character.isUpperCase(c)) 
                {
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                }
            }
        }
        catch (AWTException ex) 
        {
            Stage ErrorStage = new Stage();
            HBox inside = new HBox();
            Scene ErrorScene = new Scene(inside,menuSize,menuSizeTwo);
            Text error = new Text("The Bot Is A N G E R");
            inside.getChildren().add(error);
            ErrorStage.setScene(ErrorScene);
            ErrorStage.show();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
