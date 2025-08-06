//package com.mostapkbet.client;
//
//import android.content.Context;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//
//import com.my.tracker.config.AntiFraudConfig;
//import com.walhalla.ui.DLog;
//
//public class s0 {
//    static final class a implements SensorEventListener {
//
//        private final SensorManager sensorManager;
//        private final AntiFraudConfig antiFraudConfig;
//        private final Processor processor;
//
//
//        public static a a(
//                Processor mVar,
//                Context context, AntiFraudConfig antiFraudConfig) {
//            SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
//            if (sensorManager == null) {
//                return null;
//            }
//            return new a(
//                    mVar,
//                    sensorManager, antiFraudConfig);
//        }
//
//        a(
//                Processor mVar,
//                SensorManager sensorManager, AntiFraudConfig antiFraudConfig) {
//            this.processor = mVar;
//            this.sensorManager = sensorManager;
//            this.antiFraudConfig = antiFraudConfig;
//        }
//
//        @Override
//        public void onSensorChanged(SensorEvent sensorEvent) {
//            switch (sensorEvent.sensor.getType()) {
//                case Sensor.TYPE_MAGNETIC_FIELD:
//                    Processor processor1 = this.processor;
//                    float[] values = sensorEvent.values;
//                    processor1.b(values[0], values[1], values[2]);
//                    DLog.d("SensorHandler: magnetometer - " + sensorEvent.values[0] + ", " + sensorEvent.values[1] + ", " + sensorEvent.values[2]);
//                    return;
//                case 3:
//                case 7:
//                default:
//                    return;
//                case Sensor.TYPE_GYROSCOPE:
//                    Processor processor = this.processor;
//                    float[] fArr2 = sensorEvent.values;
//                    processor.a(fArr2[0], fArr2[1], fArr2[2]);
//                    DLog.d("SensorHandler: gyroscope - " + sensorEvent.values[0] + ", " + sensorEvent.values[1] + ", " + sensorEvent.values[2]);
//                    return;
//                case Sensor.TYPE_LIGHT:
//                    this.processor.a(sensorEvent.values[0]);
//                    DLog.d("SensorHandler: light - " + sensorEvent.values[0]);
//                    return;
//                case Sensor.TYPE_PRESSURE:
//                    this.processor.b(sensorEvent.values[0]);
//                    DLog.d("SensorHandler: pressure - " + sensorEvent.values[0]);
//                    return;
//                case Sensor.TYPE_PROXIMITY:
//                    this.processor.c(sensorEvent.values[0]);
//                    DLog.d("SensorHandler: proximity - " + sensorEvent.values[0]);
//                    return;
//            }
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//        }
//    }
//
//
//}