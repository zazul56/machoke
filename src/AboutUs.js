import liftingWeights from './liftingWeights.jpeg'

const AboutUs = () => {
    return(
        <div className="">
            <h2 className="rows-title">O nama</h2>
            <div className="rows-container">
                <div className="about-text">
                    <p>Machoke je mali, no moćan team vrhunskih sportaša i stručnjaka koji se bavi pomaganjem ljudima sa njihovim fitness ciljevima na međunarodnoj razini. Uz neprekidnu teorijsku te praktičnu edukaciju kroz rad na sebi samima i našim klijentima došli smo do izvanrednih sportskih postignuća.</p>
                    <p>Naša strast za pomaganjem i promjenom Vaših života dovodi nas do udrživanja u tim koji ima znanje i mogućnosti naslanja našeg postojanja na pozitivnom promjeni zdravlja i kvaliteti, a kroz Vašu konzistenciju, propatni dolazi i željeni izgled – bio u cilju fitness, powerlifting ili bodybuilding. Na Vama je da se zadatog pridržavate, a mi ćemo se pobrinuti za detalje. Neka nas možak i tvoje tijelo dođu do željenih rezultata.</p>
                </div>
                
                <aside className="main-banner">
                    <img src={liftingWeights} className='lifting-weights' />
                </aside>
            </div>
        </div>
        
    )
}

export default AboutUs