package edu.asu.sharpc2b.transform;

public class HeDExporterFactory {

    public static enum HED_EXPORT_FORMATS {
        HED_XML;
    }

    public static HeDExporter getExporter( HED_EXPORT_FORMATS format ) {
        switch ( format ) {
            case HED_XML:
                default:
                    return new OOwl2HedDumper();
        }
    }

    public static HeDExporter getExporter( String format ) {
        return getExporter( HED_EXPORT_FORMATS.valueOf( format ) );
    }

}
