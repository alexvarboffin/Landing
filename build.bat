@echo off

echo Generating Release APK...
call gradlew :CINEMA:KINOBAY:assembleRelease

echo.
echo Generating Release AAB...
call gradlew :CINEMA:KINOBAY:bundleRelease

echo.
echo Build finished.
echo APK is in build/outputs/apk/release/
echo AAB is in build/outputs/bundle/release/

start build/outputs/bundle/release/

pause
