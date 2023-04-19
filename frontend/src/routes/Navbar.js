import React from "react";
import { Nav, NavItem, NavLink, Navbar, NavbarBrand } from "reactstrap";


function NavbarComponent(props) {
    return (
        <div>
            <Navbar color="dark" dark={true} container="fluid">
                <NavbarBrand href="/home">Ecommerce</NavbarBrand>
                <Nav>
                    <NavItem>
                        <NavLink href="/login">Login</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink href="/">Suporte</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink href="/">Carrinho</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink href="/">Perfil</NavLink>
                    </NavItem>
                </Nav>
            </Navbar>
        </div>
    )
}

export default NavbarComponent;