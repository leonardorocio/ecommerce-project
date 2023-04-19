import React, { useEffect, useState } from "react";
import axios from "axios";
import NavbarComponent from "./Navbar";
import { Input, InputGroup, InputGroupText } from "reactstrap";
import Swal from "sweetalert2";
import { Link, Route, useNavigate } from "react-router-dom";
import ProductCard from "./ProductCard";
import Product from "./Product";

export default function Home() {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const navigate = useNavigate();

  const jwt = localStorage.getItem("JWT");

  const fetchProducts = () => {
    axios
    .get("http://localhost:8080/products", {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    })
    .then((response) => {
      localStorage.setItem("products", JSON.stringify(response.data));
      setProducts(response.data);
    })
    .catch((error) => {
      if (error.response.status === 401) {
        Swal.fire({
          title: "Usuário não autenticado. Indo para Login",
          showConfirmButton: true,
          timer: 3000,
          timerProgressBar: true,
        }).then((result) => {
          navigate("/");
        });
      }
    });
  };

  const fetchCategories = () => {
    axios
      .get("http://localhost:8080/category", {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      })
      .then((response) => {
        setCategories(response.data);
      })
      .catch((error) => {
        if (error.response.status === 401) {
          Swal.fire({
            title: "Usuário não autenticado. Indo para Login",
            showConfirmButton: true,
            timer: 3000,
            timerProgressBar: true,
          }).then((result) => {
            navigate("/");
          });
        }
      });
  };

  useEffect(() => {
    fetchCategories();
    fetchProducts();
  }, []);

  const categoryList = categories.map((category) => {
    return (
      <li key={category.id}>
        <Input type="checkbox"></Input>
        {category.name}
      </li>
    );
  });

  const productList = products.map((product, index) => {
    return (
      <li className="product" key={product.product_id}>
        <Link
          to={{
            pathname: `/product/${index}`,
          }}

          // style={{textDecoration: 'none'}}
        >
          <ProductCard product={product}></ProductCard>
        </Link>
      </li>
    );
  });

  return (
    <div>
      <NavbarComponent></NavbarComponent>
      <div className="productsPage">
        <div className="sideBarFilters">
          <ul>{categoryList}</ul>
        </div>

        <div className="productsSection">
          <div className="searchBar">
            <InputGroup>
              <InputGroupText>Search</InputGroupText>
              <Input type="text"></Input>
            </InputGroup>
          </div>

          <div className="showProducts">
            <ul>{productList}</ul>
          </div>
        </div>
      </div>
    </div>
  );
}

// export default Home;
