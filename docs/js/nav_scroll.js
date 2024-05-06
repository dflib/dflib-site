"use strict";

/**
 * A flag indicating that the user is scrolling down the page. 
 * It is needed in order to track how the transition to the section occurs, there are two options: 
 * 1) clicking on a navigation element (userScrolling = true; the toggleActive function is not executed )
 * 2) content scrolling (userScrolling = false; toggleActive function is executed) ))
 */
var userScrolling = false;

window.addEventListener(
    'load', 
    () => {
        // Create a list of all headings with an id attribute
        var trackedSections = document.querySelectorAll('.tracked-section');
        
        // Get the navigation links container
        var navLinksBox = document.querySelector('.sectlevel1');

        // Create an observer for each heading
        if(trackedSections){
            trackedSections.forEach(element => {
                createObserver(element);
            });
        }

        // Add an event listener to the navigation links container
        if(navLinksBox){
            navLinksBox.addEventListener('click', (event) => {
                event.preventDefault();
                userScrolling = true;
                var targetLink = event.target.closest('a');
                if (!targetLink) return;
    
                requestAnimationFrame(
                    () => {
                        var id = targetLink.getAttribute('href').slice(1);
                        var targetSection = document.getElementById(id);
                        scrollToSectionAndCheckEnd(targetSection, targetLink);
                    }
                );
            });
        }
       
    }
)



/**
 * Scroll to the target section and check when the scrolling ends
 * @param {HTMLElement} targetSection
 */
function scrollToSectionAndCheckEnd(targetSection, targetLink) {
    var targetPosition = targetSection.getBoundingClientRect().top + window.pageYOffset;
    window.scrollTo({ top: targetPosition, behavior: 'smooth' });

    var lastPosition = -1;
    var requestAnimationFrameId;

    var checkScrollEnd = function() {
        if (lastPosition === window.pageYOffset || 
            (window.innerHeight + window.pageYOffset) >= document.body.offsetHeight) {
            var prevActiveEls = document.querySelectorAll('.sectlevel1 a.active');
            prevActiveEls?.forEach(element => {
                element.classList.remove('active');
            });
            userScrolling = false;
            targetLink.classList.add('active');
            cancelAnimationFrame(requestAnimationFrameId); // Останавливаем проверку
        } else {
            lastPosition = window.pageYOffset;
            requestAnimationFrameId = requestAnimationFrame(checkScrollEnd);
        }
    };

    requestAnimationFrameId = requestAnimationFrame(checkScrollEnd);
}

/**
 * Create a new IntersectionObserver instance
 * @param {HTMLElement} targetElement
 */
var createObserver = (targetElement) =>{
    var observer;
    var options = {
        root: document,
        rootMargin: '-5% 0px -90% 0px',
    };
    observer = new IntersectionObserver(handleIntersect, options);
    observer.observe(targetElement);
}

/**
 * Handle the intersection of the observed element
 * @param {IntersectionObserverEntry[]} entries
 */
var handleIntersect = (entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting && !userScrolling ) {
        requestAnimationFrame(() => toggleActive(entry));
      }
    });
};

/**
 * Switching the active position of navigation links
 * @param {IntersectionObserverEntry} entry
 */
var toggleActive = (entry) => {
    var id = entry.target.id;
    var prevActive = document.querySelector('.sectlevel1 a.active');
    var activeLink = document.querySelector(`.sectlevel1 a[href="#${id}"]`);
    prevActive?.classList.remove('active');
    activeLink?.classList.add('active');

    if(window.innerWidth > 768 && activeLink){
        activeLink.addEventListener('transitionend', () => {
            scrollToActive(activeLink);
        }, { once: true });
    }
};

/**
 * Scroll to the active navigation link
 * @param {HTMLElement} activeLink
 */
var scrollToActive = (activeLink) => {
    var navBox = document.querySelector('#toc');
    var bottomPos = activeLink.getBoundingClientRect().bottom;
    Promise.resolve(1).then(() => {
        if(bottomPos >= (navBox.offsetHeight - 50) || bottomPos <= 80){
            activeLink.scrollIntoView({ block: "center", behavior: "auto" });
        }
    });
}