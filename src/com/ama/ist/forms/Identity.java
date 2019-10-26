package com.ama.ist.forms;

import com.ama.ist.annotations.FormField;
import com.ama.ist.annotations.Password;
import com.ama.ist.annotations.Validator;
import com.ama.ist.annotations.Form;

@Form(
    label = "Identity Form", 
    id = "frmIdentity"
)
public class Identity {

  @Validator("!=null")
  @Validator(".length>0")
  @FormField(label = "Name", id="txtName")
  public String name;

  @Validator("!=null")
  @Validator(".length>0")
  @FormField(label = "Surname", id="txtSurname")
  public String surname;

  @Validator(">19")
  @FormField(label = "Age", id="txtAge")
  public int age;
  
  @Validator("!=null")
  @Password
  @FormField(label="Password",id="txtPassword")
  public String password;
}