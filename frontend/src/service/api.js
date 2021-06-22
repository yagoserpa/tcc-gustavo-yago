import Axios from "axios";

export const api = Axios.create({
  baseURL: "http://localhost:8080/api/v1/",
  headers: {
    Accept: "application/json",
    Authorization: "Bearer " + localStorage.getItem("@GeProFi:token"),
  },
});

export const apiGet = async (url, callback) => {
  const resposta = await api.get(url);
  callback(resposta.data);
};

export const apiPost = async (url, body, callback) => {
  const resposta = await api.post(url, body);
  callback(resposta.data);
};

export const getUser = async (id, callback) => {
  const response = await api.get(`user/${id}`);
  callback(response.data);
};

export const getFieldOfInterests = async (callback) => {
  const response = await api.get("/field");
  callback(response.data);
};

export const getFieldOfInterestUsers = async (id, callback) => {
  const response = await api.get(`/field/${id}/users`);
  callback(response.data);
};

export const getProjectsFromUser = async (callback) => {
  const response = await api.get("user/projects");
  callback(response.data);
};
