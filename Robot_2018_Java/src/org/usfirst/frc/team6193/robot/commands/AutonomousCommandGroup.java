package org.usfirst.frc.team6193.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousCommandGroup extends CommandGroup {


	// Constructor
    public AutonomousCommandGroup() {

    }
    /**
     * 
     * @param gameData "xxx" if no data received, otherwise "RRR","LLL","RLR","LRL"
     * @param Side The side of the field the robot was put on during setup. false=Left, true=Right
     * @param Switch Alliance agreement to go for Switch no mater what
     * @param Scale Alliance agreement to go for Scale no mater what
     */
    public void selectAutonomousPlay(String gameData, boolean Side, boolean Switch, boolean Scale) {
    	int play = 0;
    	if(gameData == "xxx") { // Cross the line if no good data
    		play = 0;
    	}else if(Switch) { // go for switch
    		char swChar = gameData.charAt(0);
    		if(Side) { // Right
    			if(swChar == 'R') { // Switch is on Right and robot is on the right
    				
    			}else { // Switch is on the left and robot is on the right
    				
    			}
    		}else {  // Left
    			
    		}
    	}else if(Scale){ // go for scale
    		char scChar = gameData.charAt(1);
    		if(Side) { // Right
    			
    		}else {  // Left
    			
    		}
    	}else { // Get the first thing on your side of the field, otherwise just cross
    		if(Side) {// Right
    			if(gameData.charAt(0) == 'R') { // Go for switch
    				
    			}else if(gameData.charAt(1) == 'R') { // Go for scale
    				
    			}else { // Cross
    				
    			}
    		}else { // Left
    			
    		}
    	}
    	// Choose the play from the logic above.
    	switch(play) {
    	case 0: // Cross the line
    		break; 
    	case 1:
    		break;
    	}
    	
    }
    
}
