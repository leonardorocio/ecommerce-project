import React from "react";
import {
    Card,
    CardBody,
    CardSubtitle,
    CardText,
    CardTitle,
  } from "reactstrap";

function ProductCard(props) {
  return (
    <Card>
      <CardBody>
        <CardTitle>{props.product.name}</CardTitle>
        <CardSubtitle>{props.product.price}</CardSubtitle>
        <CardText>{props.product.description}</CardText>
      </CardBody>
    </Card>
  );
}

export default ProductCard;