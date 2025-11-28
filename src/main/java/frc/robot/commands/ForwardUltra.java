// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.XRPDrivetrain;
import frc.robot.subsystems.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class ForwardUltra extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final XRPDrivetrain m_subsystem;

  private final Ultrasonic m_ultra;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ForwardUltra(XRPDrivetrain subsystem, Ultrasonic ultra) {
    m_subsystem = subsystem;
    m_ultra = ultra;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_subsystem.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_subsystem.arcadeDrive(1, 0);
    SmartDashboard.putNumber("LeftMotor(Inch)", m_subsystem.getLeftDistanceInch());
    SmartDashboard.putNumber("RightMotor(Inch)", m_subsystem.getRightDistanceInch());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_ultra.isCloserThanCm(25.0);
  }
}
