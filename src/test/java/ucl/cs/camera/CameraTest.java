package ucl.cs.camera;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class CameraTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  Sensor sensor = context.mock(Sensor.class);
  MemoryCard memoryCard  = context.mock(MemoryCard.class);

  Camera camera = new Camera(sensor, memoryCard);

  @Test
  public void switchingTheCameraOnPowersUpTheSensor() {

    context.checking(new Expectations(){{
      exactly(1).of(sensor).powerUp();
    }});

    camera.powerOn();
  }

  @Test
  public void switchingCameraOffWhileNotWriting(){

    context.checking(new Expectations(){{
      exactly(1).of(sensor).powerDown();
    }});

    camera.powerOff();
  }

  @Test
  public void pressingShutterWhileOffDoesNothing(){

    context.checking(new Expectations(){{
      exactly(1).of(sensor).powerDown();
      exactly(0).of(sensor).readData();
    }});
    camera.powerOff();
    camera.pressShutter();
  }

  @Test
  public void pressingShutterWhileOnCopiesData(){
    context.checking(new Expectations(){{
      exactly(1).of(sensor).readData(); will(returnValue(new byte[0]));
      exactly(1).of(sensor).powerUp();
      exactly(1).of(memoryCard).write(new byte[0]);
      exactly(1).of(sensor).powerDown();
    }});

    camera.powerOn();
    camera.pressShutter();
  }

  public void turningCameraOffDoesNotInterruptWrite(){
    context.checking(new Expectations(){{
      exactly(1).of(sensor).powerDown();
    }});
    camera.pressShutter();
    camera.powerOff();
  }



}
