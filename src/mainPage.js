import liftingWeights from './liftingWeights.jpeg'
import Signup from './Pages/SignUp/SignUp'
import { useNavigate } from 'react-router-dom'; 

const MainPage = () => {
    const navigate = useNavigate();

    const navigateToSignup = () =>{
        navigate('/signup')
    }



    return (
    <main className="rows-container">
        <div className="text-container">
            <h1>Podignite svoje standarde i svoje tegove.</h1>
            <p>Pridruži nam se i postavi nove granice.</p>
            <button className="signup-button" onClick={navigateToSignup}>Upiši se</button>
        </div>


        <aside className="main-banner">
            <img src={liftingWeights} className='lifting-weights' />
        </aside>
    </main>
    )
}

export default MainPage


