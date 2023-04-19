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
import Swal from "sweetalert2";

function SignUp(props) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const navigate = useNavigate();

  function handleChangeEmail(event) {
    setEmail(event.target.value);
  }

  function handleChangePassword(event) {
    setPassword(event.target.value);
  }

  function handleChangeName(event) {
    setName(event.target.value);
  }

  function handleSubmit(event) {
    const confirmPassword = document.getElementById("passwordConfirm").value;
    console.log(password + " " + confirmPassword);
    if (password !== confirmPassword) {
      console.log("ue");
      Swal.fire({
        title: "Senhas não são iguais",
        showConfirmButton: true,
      });
    } else {
      axios
        .post("http://localhost:8080/users", {
          name: name,
          email: email,
          password: password,
        })
        .then((response) => {
          console.log("Teste");
          localStorage.setItem("JWT", response.data.accessToken);
          navigate("/login");
        })
        .catch((error) => {
          console.log(error);
        });
      event.preventDefault();
    }
  }

  return (
    <div>
      <div className="LoginForm">
        <Form className="form" onSubmit={handleSubmit}>
          <FormGroup>
            <Label for="nome">Nome:</Label>
            <InputGroup>
              <Input
                type="nome"
                name="nome"
                id="nome"
                placeholder="Nome Sobrenome"
                onChange={handleChangeName}
              />
            </InputGroup>
          </FormGroup>
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
            <Label for="password">Senha:</Label>
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
            <Label for="password">Confirmar senha:</Label>
            <InputGroup>
              <Input
                type="password"
                name="passwordConfirm"
                id="passwordConfirm"
                placeholder="********"
              />
            </InputGroup>
          </FormGroup>
          <FormGroup>
            <Button type="submit" color="primary">
              Submit
            </Button>
          </FormGroup>
          <FormText>
            Já possui uma conta? Faça o <a href="/login">login</a>
          </FormText>
        </Form>
      </div>
    </div>
  );
}

export default SignUp;
