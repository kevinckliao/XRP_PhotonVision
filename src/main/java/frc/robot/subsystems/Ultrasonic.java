package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.filter.LinearFilter;

public class Ultrasonic extends SubsystemBase {
  // === 調整區 ===
  public static final int kAnalogChannel = 2;     // 你的 XRP 超音波接在 AnalogInput 2
  private static final double kMinMm = 20.0;      // 0V 對應 20 mm
  private static final double kMaxMm = 4000.0;    // 5V 對應 4000 mm
  private static final double kVref = 5.0;        // XRP 的類比輸入滿刻度 5V
  private static final double kRangeMm = kMaxMm - kMinMm; // 3980 mm

  private final AnalogInput m_ain = new AnalogInput(kAnalogChannel);

  // 取樣濾波（移動平均）：取最近 N 筆電壓平均，降低抖動
  private static final int kAvgWindow = 10;
  private final LinearFilter m_voltageAvg = LinearFilter.movingAverage(kAvgWindow);

  private double m_lastAvgVoltage = 0.0;

  public Ultrasonic() {
    // XRP/AnalogInput 會自動處理標準取樣；這裡再做一層平均更穩定
  }

  /** 原始電壓（未濾波） */
  public double getVoltageRaw() {
    return m_ain.getVoltage(); // 單位：V
  }

  /** 濾波後電壓（建議用這個做距離換算） */
  public double getVoltageAvg() {
    double v = clamp(m_ain.getVoltage(), 0.0, kVref);
    m_lastAvgVoltage = m_voltageAvg.calculate(v);
    return clamp(m_lastAvgVoltage, 0.0, kVref);
  }

  /** 換算距離（mm） */
  public double getDistanceMm() {
    double v = getVoltageAvg(); // 已濾波且 clamp
    return kMinMm + (v / kVref) * kRangeMm; // 線性映射
  }

  /** 換算距離（cm） */
  public double getDistanceCm1() {
    return getDistanceMm() / 10.0;
  }

  /** 物體是否小於指定距離（cm） */
  public boolean isCloserThanCm(double thresholdCm) {
    return getDistanceCm1() <= thresholdCm;
  }

  @Override
  public void periodic() {
    // 方便你在 Shuffleboard/SmartDashboard 觀察數值
    SmartDashboard.putNumber("Ultrasonic/VoltageRaw(V)", clamp(getVoltageRaw(), 0.0, kVref));
    SmartDashboard.putNumber("Ultrasonic/VoltageAvg(V)", m_lastAvgVoltage);
    SmartDashboard.putNumber("Ultrasonic/Distance(cm)", getDistanceCm1());
  }

  private static double clamp(double x, double lo, double hi) {
    return Math.max(lo, Math.min(hi, x));
  }
}
