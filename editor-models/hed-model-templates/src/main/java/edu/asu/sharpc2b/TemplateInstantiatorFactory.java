package edu.asu.sharpc2b;

public class TemplateInstantiatorFactory {

    public static TemplateInstantiator newTemplateInstantiator() {
        return new TemplateInstantiatorImpl();
    }
}
