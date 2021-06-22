import React from "react";
import { useContext } from "react";
import { useState } from "react";
import { api } from "../service/api";

export function useAuth() {
  const context = useContext(AuthContext);

  return context;
}

const AuthContext = React.createContext({});

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(localStorage.getItem("@GeProFi:token"));

  async function login(username, password) {
    const body = { username: username, password: password };
    const response = await api.post("/auth", body);
    const token = response.data.access_token;
    api.defaults.headers.Authorization = `Bearer ${token}`;
    localStorage.setItem("@GeProFi:token", token);
    setUser(token);
  }

  function logout(callback) {
    api.defaults.headers.Authorization = null;
    localStorage.removeItem("@GeProFi:token");
    setUser(null);
    callback();
  }

  return (
    <AuthContext.Provider value={{ signed: Boolean(user), login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
