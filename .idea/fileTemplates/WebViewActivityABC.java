package ${PACKAGE_NAME};

import android.app.Activity;
import android.os.Bundle;

#set( $PROGRESS_ENABLED = true )
#set( $CHECK_CONNECTION = true )
#parse("File Header.java")
public class ${NAME} extends Activity {
    public static final boolean SWIPE_ENABLED = ${SWIPE_ENABLED};
    public static final boolean PROGRESS_ENABLED = ${PROGRESS_ENABLED};
    public static final boolean CHECK_CONNECTION = ${CHECK_CONNECTION};
}