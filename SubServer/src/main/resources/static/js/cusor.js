// Wait for the document to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    // Get the navigation links with corrected selectors
    const contactLink = document.querySelector('a[href="#lien-he"]');
    const helpLink = document.querySelector('a[href="#tro-giup"]');
    const footer = document.querySelector('footer');
    
    // Debug to check if elements are found
    console.log('Contact link found:', contactLink);
    console.log('Help link found:', helpLink);
    console.log('Footer found:', footer);
    
    // Function to scroll to footer
    function scrollToFooter(e) {
        e.preventDefault();
        console.log('Scrolling to footer...');
        if (footer) {
            footer.scrollIntoView({ behavior: 'smooth' });
        } else {
            console.error('Footer element not found');
        }
    }
    
    // Add click event listeners to the links
    if (contactLink) {
        contactLink.addEventListener('click', scrollToFooter);
    } else {
        console.error('Contact link not found');
    }
    
    if (helpLink) {
        helpLink.addEventListener('click', scrollToFooter);
    } else {
        console.error('Help link not found');
    }
});