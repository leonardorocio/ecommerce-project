import React, { useState } from "react";
import axios from "axios";
import {
  Button,
  Form,
  InputGroup,
  Label,
  FormGroup,
  Input,
  FormText,
} from "reactstrap";
import "../styles/App.css";
import { useNavigate } from "react-router-dom";

function LoginForm(props) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  function handleChangeEmail(event) {
    setEmail(event.target.value);
  }

  function handleChangePassword(event) {
    setPassword(event.target.value);
  }

  function handleSubmit(event) {
    axios
      .post("http://localhost:8080/auth/login", {
        email: email,
        password: password,
      })
      .then((response) => {
        localStorage.setItem("JWT", response.data.accessToken);
        navigate("/home");
      })
      .catch((error) => {
        console.log(error);
      });
    event.preventDefault();
  }

  return (
    <div>
      <div className="LoginForm">
        <Form className="form" onSubmit={handleSubmit}>
          <FormGroup>
            <Label for="email">Email:</Label>
            <InputGroup>
              <Input
                type="email"
                name="email"
                id="email"
                placeholder="example@email.com"
                onChange={handleChangeEmail}
              />
            </InputGroup>
          </FormGroup>
          <FormGroup>
            <Label for="password">Password:</Label>
            <InputGroup>
              <Input
                type="password"
                name="password"
                id="password"
                placeholder="********"
                onChange={handleChangePassword}
              />
            </InputGroup>
          </FormGroup>
          <FormGroup>
            <Button type="submit" color="primary">
              Submit
            </Button>
          </FormGroup>
          <FormText>Não possui uma conta ainda? Faça o <a href="/signup">cadastro</a></FormText>
        </Form>
      </div>
    </div>
  );
}

export default LoginForm;
