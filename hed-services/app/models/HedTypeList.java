package models;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import models.ex.ConvertJsonToJavaException;
import models.ex.ModelDataFileNotFoundException;
import play.libs.Json;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * User: rk Date: 9/26/13
 */
public class HedTypeList
        extends SharpType
{

//    public String mtype = getClass().getSimpleName();

    public List<HedType> hedTypes;

    //========================================================================================

}
