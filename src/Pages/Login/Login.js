import { useState } from "react";
import { getRequestFetchingLogic, fetchRequestLogin } from "../../Api";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../Auth/AuthProvider";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const credentials = {
      username: username,
      password: password,
    };
    const data = await fetchRequestLogin("/api/auth/login", credentials);
    if (data.token) {
      setUsername("");
      setPassword("");
      navigate("/");

      const userRole = await getRequestFetchingLogic(
        `/api/users/role/${credentials.username}`
      );

      login(data.token, userRole);
    } else {
      console.log("no token");
    }
  };

  return (
    <div className="container">
      <form onSubmit={handleSubmit}>
        <label className="loginTitle">Username: </label>
        <input
          type="text"
          placeholder="username"
          onChange={(e) => setUsername(e.target.value)}
        />

        <label className="loginTitle">Password: </label>
        <input
          type="password"
          placeholder="password"
          onChange={(e) => setPassword(e.target.value)}
        />

        <button type="submit" className="submitButton">
          Login
        </button>
      </form>
    </div>
  );
};

export default Login;
