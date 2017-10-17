package ucl.cs.camera;

public class Camera {
  private Sensor sensor;
  private MemoryCard memoryCard;
  private Boolean on = false;
  private Boolean writing = false;
  private WriteListener writeListener = new WriteListener() {
      @Override
      public void writeComplete() {
          writing = false;
          notify();
      }
  };

  public Camera(Sensor s, MemoryCard m){
    sensor = s;
    memoryCard = m;
  }

  public void pressShutter() {
    if(on){
      writing = true;
      memoryCard.write(sensor.readData());
      writeListener.writeComplete();
      sensor.powerDown();
    }
  }

  public void powerOn() {
    on = true;
    sensor.powerUp();
  }

  public void powerOff() throws InterruptedException {
    on = false;
    if (!writing){
      sensor.powerDown();
    }
    else{
        wait();
        sensor.powerDown();
    }
  }
}

