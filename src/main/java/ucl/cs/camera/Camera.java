package ucl.cs.camera;

public class Camera implements WriteListener{
  private Sensor sensor;
  private MemoryCard memoryCard;
  private Boolean on = false;
  private Boolean writing = false;

  public Camera(Sensor s, MemoryCard m){
    sensor = s;
    memoryCard = m;
  }

  public void pressShutter() {
    if(on){
      writing = true;
      memoryCard.write(sensor.readData());
      sensor.powerDown();
    }
  }

  public void powerOn() {
    on = true;
    sensor.powerUp();
  }

  public void powerOff(){
    on = false;
    if (!writing){
      sensor.powerDown();
    }
  }

    @Override
    public void writeComplete() {
        writing = false;
    }
}

