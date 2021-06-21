import React from "react";
import { useEffect } from "react";
import { useContext } from "react";
import { useState } from "react";
import { api } from "../service/api";

export function useAuth() {
  const context = useContext(AuthContext);

  return context;
}

const AuthContext = React.createContext({});

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("@GeProFi:token");

    if (token) {
      setUser(token);
      api.defaults.headers.Authorization = `Bearer ${token}`;
    }
  }, []);

  async function login(username, password, callback) {
    const body = { username: username, password: password };
    const response = await api.post("/auth", body);
    const token = response.data.access_token;
    setUser(token);
    localStorage.setItem("@GeProFi:token", token);
    api.defaults.headers.Authorization = `Bearer ${token}`;
    callback(response.data);
  }

  function logout(callback) {
    setUser(null);
    localStorage.removeItem("@GeProFi:token");
    api.defaults.headers.Authorization = null;
    callback();
  }

  return (
    <AuthContext.Provider
      value={{ signed: Boolean(user), user: user, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
};
