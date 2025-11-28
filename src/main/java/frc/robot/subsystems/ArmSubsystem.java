package frc.robot.subsystems;

import edu.wpi.first.wpilibj.xrp.XRPServo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    private final XRPServo m_Servo;

    public ArmSubsystem(){
        m_Servo = new XRPServo(0);
    }

    @Override
    public void periodic(){}

    public void setPosition(double position){
        m_Servo.setPosition(position);
    }
}
