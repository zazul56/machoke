import { NavLink } from "react-router-dom";
import mach from "./mach.png";
import { useAuth } from "./Auth/AuthProvider";
const Navbar = () => {
  const { isLoggedIn, userRole, logout } = useAuth();

  return (
    <nav className="navbar">
      <div className="container">
        <div className="logo">
          <img src={mach} className="mach-logo" alt="machoke" />
          <p className="logo-text">Machoke</p>
        </div>
        <div className="nav-elements">
          <ul>
            <li>
              <NavLink to="/">Pocetna</NavLink>
            </li>
            <li>
              <NavLink to="/blog">Clanci</NavLink>
            </li>
            <li>
              <NavLink to="/forum">Forum</NavLink>
            </li>
            <li>
              <NavLink to="/contact">Kontakt</NavLink>
            </li> 
            {userRole === "ROLE_ADMIN" && (
              <li>
                <NavLink to="/users">Korisnici</NavLink>
              </li>
            )}
            <li>
              {isLoggedIn ? (
                <button onClick={logout}>Odjava</button>
              ) : (
                <NavLink to="/login">Prijava</NavLink>
              )}
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
