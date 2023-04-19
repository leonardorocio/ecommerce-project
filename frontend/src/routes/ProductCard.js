import React from "react";
import {
    Card,
    CardBody,
    CardImg,
    CardSubtitle,
    CardText,
    CardTitle,
  } from "reactstrap";

function ProductCard(props) {
  return (
    <Card>
      <CardImg src='https://t3.ftcdn.net/jpg/02/48/42/64/240_F_248426448_NVKLywWqArG2ADUxDq6QprtIzsF82dMF.jpg' />
      <CardBody>
        <CardTitle>R$ {props.product.price}</CardTitle>
        <CardSubtitle>{props.product.price}</CardSubtitle>
        <CardText>{props.product.description}</CardText>
      </CardBody>
    </Card>
  );
}

export default ProductCard;