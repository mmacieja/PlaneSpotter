console.log("app.js loaded");
const registerSection = document.getElementById("registerSection")
const loginSection = document.getElementById("logSection")
const menuSection = document.getElementById("menu");
const app = document.getElementById("app");
const collectionSection = document.getElementById("collectionSection")
const collection = document.getElementById("collection")
const updateSection = document.getElementById("update");
let currentUser = null;



const register = document.getElementById("register");
const registerMessage = document.getElementById("registerMessage");

register.addEventListener("click", async function(){
    const username = document.getElementById("registerUsername").value
    const email = document.getElementById("registerEmail").value;
    const password = document.getElementById("registerPassword").value;

    const response = await fetch("/user/register", {
        method: "POST",
        headers:{"Content-Type": "application/json"}, body: JSON.stringify({
                "username": username,
                "email": email,
                "password": password,
    })


});

    if (!response.ok){
        const error = await response.json();
        registerMessage.textContent = JSON.stringify(error);
        return;
    }

    registerMessage.textContent = "Registration successfull. Please log in"

    registerSection.style.display = "none";

});

const login = document.getElementById("login");
const loginMessage = document.getElementById("loginMessage");


login.addEventListener("click", async function() {
    const username = document.getElementById("logUsername").value
    const password = document.getElementById("logPassword").value;

    const response = await fetch("/user/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"}, body: JSON.stringify({
            "username": username,
            "password": password,
        })
    })
    if (!response.ok) {
        const error = await response.json();
        loginMessage.textContent = JSON.stringify(error);
        return;
    }

    currentUser = await response.json();

    alert("Logged in " + currentUser.username);

    registerSection.style.display = "none";
    loginSection.style.display= "none";

    menuSection.style.display = "block"

})

document.getElementById("identifyMenu").addEventListener("click", function(){
    menuSection.style.display = "none";
    app.style.display = "block";
})


const identify = document.getElementById("identify");

const result = document.getElementById("result");

let plane = null;

identify.addEventListener("click", async function () {
    const latitude = document.getElementById("latitude").value;
    const longitude = document.getElementById("longitude").value;
    const bearing = document.getElementById("bearing").value;

    const response = await fetch(`/opensky/findPlane?la=${latitude}&lo=${longitude}&bearing=${bearing}`);


    if (!response.ok){
        alert("No plane found");
        return;
    }

    plane = await response.json();
    result.style.display ="block";

    app.style.display = "none";

    result.innerHTML = `<h2>Plane found</h2><p>ICAO24: ${plane.icao24}</p><p>Callsign: ${plane.callsign}</p><p>Origin: ${plane.origin_country}</p><button id="save">Save sighting</button><button id="backSighting">Go back</button>`;

    document.getElementById("save").addEventListener("click", async function(){

        const latitude = document.getElementById("latitude").value;
        const longitude = document.getElementById("longitude").value;

        const response = await fetch("/sighting", {method: "POST",
            headers: {"Content-Type": "application/json"}, body: JSON.stringify({
                "icao24": plane.icao24,
                "callsign": plane.callsign,
                "latitude": Number(latitude),
                "longitude": Number(longitude)
            })});

        if(!response.ok){
            alert("The sighting could not be saved.")
        }
        alert("The sighting was saved.")

    })

    document.getElementById("backSighting").addEventListener("click", function(){
        menuSection.style.display = "block"

        result.style.display ="none";

    })




})

async function showSightings(){

    const response = await fetch("/sighting/all");

    if(!response.ok){
        collection.textContent = "No sightings";
        return;
    }

    const sightings = await response.json();

    collection.innerHTML = "";


    let index = 1;

    for (const sighting of sightings){
        collection.innerHTML += `<div><h3>Sighting ${index}</h3>
            <p>ICAO24: ${sighting.icao24}</p>
            <p>Callsign: ${sighting.callsign}</p>
            <p>Time: ${sighting.time}</p>
            <p>Location: ${sighting.latitude}, ${sighting.longitude}</p>
            <button class="delete" type="button" data-id="${sighting.id}">Delete</button>
            </div>`;

        index += 1;
    }

    const deleteButtons = document.querySelectorAll(".delete");

    deleteButtons.forEach(function(button){

        button.addEventListener("click", async function(){
            const id = button.dataset.id;
            const response = await fetch(`/sighting/${id}`,
                { method: "DELETE"}
            );

            if(!response.ok){
                alert("Could not remove the sighting.");
                return;
            }
            alert("Sighting removed successfully.");
            await showSightings();

        })
    })


}




document.getElementById("collectionMenu").addEventListener("click", async function(){

    menuSection.style.display = "none";
    collectionSection.style.display = "block";

    await showSightings();
});

const updateMessage = document.getElementById("updateMessage");
document.getElementById("updateMenu").addEventListener("click", function(){
    console.log("update button clicked");
    menuSection.style.display = "none";
    updateSection.style.display = "block";});


document.getElementById("updateButton").addEventListener("click", async function(){
    console.log("save changes clicked");

    const username = document.getElementById("updateUsername").value
    const email = document.getElementById("updateEmail").value;
    const password = document.getElementById("updatePassword").value;

    const response = await fetch(`/user/${currentUser.id}`, {
        method: "PUT",
        headers:{"Content-Type": "application/json"}, body: JSON.stringify({
            "username": username,
            "email": email,
            "password": password,
    })});
    if (!response.ok){
        const error = await response.json();
        updateMessage.textContent = JSON.stringify(error);
        return;
    }
    currentUser = await response.json();
    alert("Details updated successfully.")

});

document.getElementById("logout").addEventListener("click", async function(){

    const response = await fetch("/user/logout", {
        method: "POST"
    });


    if (!response.ok){
        alert("Logout failed");
        return;
    }
    alert("Logged out successfully.")

    currentUser = null;
    document.getElementById("logUsername").value = ""
    document.getElementById("logPassword").value = "";
    document.getElementById("registerUsername").value = ""
    document.getElementById("registerPassword").value = "";
    document.getElementById("registerEmail").value = "";
    document.getElementById("registerMessage").textContent = "";


    menuSection.style.display = "none";
    loginSection.style.display = "block"
    registerSection.style.display = "block";


})

document.getElementById("backCollection").addEventListener("click", function(){
    console.log("back clicked")
    menuSection.style.display = "block"
    collectionSection.style.display = "none";
})

console.log(document.getElementById("backCollection"));
document.getElementById("backUpdate").addEventListener("click", function(){
    menuSection.style.display = "block"
    updateSection.style.display = "none";
})
document.getElementById("backIdentify").addEventListener("click", function(){
    menuSection.style.display = "block"
    app.style.display = "none";
})