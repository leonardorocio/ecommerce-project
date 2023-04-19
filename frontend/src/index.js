import React from 'react';
import ReactDOM from 'react-dom/client';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import './index.css';
import {
  createBrowserRouter,
  RouterProvider,
} from 'react-router-dom';
import App from './routes/App';
import Home from './routes/Home';
import reportWebVitals from './reportWebVitals';

import ErrorPage from './routes/errorRoute';
import LoginForm from './routes/Login';
import SignUp from './routes/SignUp';
import Product from './routes/Product';

const router = createBrowserRouter(
  [
    {
      path: "/",
      element: <App/>,
      errorElement: <ErrorPage/>
    },
    {
      path: "/home",
      element: <Home/>,
      errorElement: <ErrorPage/>
    },
    {
      path: "/login",
      element: <LoginForm/>,
      errorElement: <ErrorPage/>
    },
    {
      path: "/signup",
      element: <SignUp/>,
      errorElement: <ErrorPage/>
    },
    {
      path: "/product/:productIndex",
      element: <Product/>,
      errorElement: <ErrorPage/>
    }
  ]
);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    {/* <App /> */}
    <RouterProvider router={router}/>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
