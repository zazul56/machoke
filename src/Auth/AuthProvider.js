import { useState, createContext, useEffect, useContext } from "react";

const AuthContext = createContext({
  isLoggedIn: false,
  userToken: null,
  userRole: null,
  login: () => {},
  logout: () => {},
  setUserRole: () => {},
});

export const useAuth = () => useContext(AuthContext);

const AuthProvider = ({ children }) => {
  const [userToken, setUserToken] = useState(localStorage.getItem("token"));
  const [userRole, setUserRole] = useState();

  const login = (token, userRole) => {
    localStorage.setItem("token", token);
    setUserRole(userRole.name || null);
    setUserToken(token);
  };

  const logout = () => {
    localStorage.removeItem("token");
    setUserToken(null);
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    setUserToken(token);
  }, []);

  return (
    <AuthContext.Provider
      value={{
        isLoggedIn: userToken,
        userToken,
        userRole,
        login,
        logout,
        setUserRole,
      }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;
