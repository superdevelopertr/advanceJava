package com.ama.ist;

import java.lang.reflect.Field;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.ama.ist.annotations.Form;
import com.ama.ist.annotations.FormField;
import com.ama.ist.annotations.Password;
import com.ama.ist.annotations.Validator;
import com.ama.ist.annotations.Validators;
import com.ama.ist.forms.Identity;

public class FormProcessor {

  ScriptEngineManager sem = new ScriptEngineManager();

  public String createForm(String className) throws ClassNotFoundException {
    Class identifyForm = Class.forName(className);
    Form form = (Form)identifyForm.getAnnotation(Form.class);

    String result = "<legend>" + form.label() + "</legend>";

    result += "<fieldset>";
    for (Field field : identifyForm.getFields()) {
      FormField metadata = field.getAnnotation(FormField.class);
      result += "<label for=\"" + metadata.id() + "\">" + metadata.label() + "</label>";

      Password metadata2 = field.getAnnotation(Password.class);
      if (metadata2 == null) {
        result += "<input type=\"text\" id=\"" + metadata.id() + "\"/><br/>";
      } else {
        result += "<input type=\"password\" id=\"" + metadata.id() + "\"/><br/>";
      }
    }

    result += "</fieldset>";
    return result;
  }

  public boolean validateForm(Identity identity)
      throws IllegalArgumentException, IllegalAccessException, ScriptException {
    boolean result = true;
    for (Field field : identity.getClass().getFields()) {
      Validators validators = field.getAnnotation(Validators.class);
      Object data = field.get(identity);
      if (validators != null) {

        for (Validator validator : validators.value()) {
          result = result && validaField(data, validator.value());
        }
      } else {
        Validator validator = field.getAnnotation(Validator.class);
        result = result && validaField(data, validator.value());
      }
    }

    //Arrays.stream(identity.getClass().getFields()).map(arg0)
    return result;
  }

  private boolean validaField(Object data, String expression) throws ScriptException {

    ScriptEngine engine = sem.getEngineByName("nashorn");
    engine.put("field", data);
    engine.eval("var result=field" + expression + ";");
    return (boolean)engine.get("result");
  }

  public static void main(String[] args)
      throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, ScriptException {

    FormProcessor form = new FormProcessor();
    String finalForm = form.createForm("com.ama.ist.forms.Identity");

    // System.out.println(finalForm);

    Identity identity = new Identity();
    identity.name = "Abdullah";
    identity.surname = "ILHANLI";
    identity.age = 25;
    identity.password = "Test1234";

    System.out.println(form.validateForm(identity));
  }
}