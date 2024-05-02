const Post = ({srcUrl, title, author, date, text, readMoreLink}) => {
    return(
        <div className="post-container">
            <img src={srcUrl}></img>
            <h3 className="post-title">{title}</h3>
            <div className="post-details">
                <span>Kreirao: {author}</span>
                <span>Datum: {date}</span>
            </div>
            <p className="post-text">{text}</p>
            <a href={readMoreLink} className="read-more">Pročitaj</a>
        </div>
    )
}

export default Post;