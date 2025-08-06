//package com.mostapkbet.client;
//
//import android.content.Context;
//import android.hardware.Sensor;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.util.Log;
//
//import com.my.tracker.config.AntiFraudConfig;
//
//public class GyroscopeHandler {
//
//    private final AntiFraudConfig antiFraudConfig;
//
//    private SensorManager sensorManager;
//    private final s0.a defaultListener;
//
////    private SensorEventListener gyroscopeListener;
////    private SensorEventListener magnetometerListener;
////    private SensorEventListener accelerometerListener;
////    private SensorEventListener lightSensorListener;
////    private SensorEventListener pressureSensorListener;
////    private SensorEventListener proximitySensorListener;
//
//    public GyroscopeHandler(Context context, AntiFraudConfig antiFraudConfig) {
//        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
//
//        Processor output = new Processor();
//        defaultListener = s0.a.a(output, context, antiFraudConfig);
//        this.antiFraudConfig = antiFraudConfig;
//        if (sensorManager != null) {
////            gyroscopeListener = createSensorListener(Sensor.TYPE_GYROSCOPE);
////            magnetometerListener = createSensorListener(Sensor.TYPE_MAGNETIC_FIELD);
////            accelerometerListener = createSensorListener(Sensor.TYPE_ACCELEROMETER);
////            lightSensorListener = createSensorListener(Sensor.TYPE_LIGHT);
////            pressureSensorListener = createSensorListener(Sensor.TYPE_PRESSURE);
////            proximitySensorListener = createSensorListener(Sensor.TYPE_PROXIMITY);
//        } else {
//            Log.e("GyroscopeHandler", "Failed to obtain SensorManager");
//        }
//    }
//
//    public void startSensorsTracking() {
//        if (antiFraudConfig.useGyroscope) {
//            startSensorTracking(Sensor.TYPE_GYROSCOPE, defaultListener);
//        }
//        if (antiFraudConfig.useMagneticFieldSensor) {
//            startSensorTracking(Sensor.TYPE_MAGNETIC_FIELD, defaultListener);//magnetometerListener
//        }
//        if (antiFraudConfig.useLightSensor) {
//            startSensorTracking(Sensor.TYPE_LIGHT, defaultListener);//lightSensorListener
//        }
//        if (antiFraudConfig.usePressureSensor) {
//            startSensorTracking(Sensor.TYPE_PRESSURE, defaultListener);//pressureSensorListener
//        }
//        if (antiFraudConfig.useProximitySensor) {
//            startSensorTracking(Sensor.TYPE_PROXIMITY, defaultListener);//proximitySensorListener
//        }
//    }
//
//    public void stopSensorsTracking() {
//        stopSensorTracking(defaultListener);
////        stopSensorTracking(magnetometerListener);
////        stopSensorTracking(accelerometerListener);
////        stopSensorTracking(lightSensorListener);
////        stopSensorTracking(pressureSensorListener);
////        stopSensorTracking(proximitySensorListener);
//    }
//
//    private void startSensorTracking(int sensorType, SensorEventListener listener) {
//        if (sensorManager != null && listener != null) {
//            Sensor sensor = sensorManager.getDefaultSensor(sensorType);
//            if (sensor != null) {
//                sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
//            } else {
//                Log.e("GyroscopeHandler", "No sensor of type " + sensorType + " found");
//            }
//        }
//    }
//
//    private void stopSensorTracking(SensorEventListener listener) {
//        if (sensorManager != null && listener != null) {
//            sensorManager.unregisterListener(listener);
//        }
//    }
//}
