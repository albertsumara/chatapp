document.addEventListener("DOMContentLoaded", () => {
    const select = document.getElementById("weather");

    const listCountries = () => {
        fetch(`/api/countries`)
            .then(res => res.json())
            .then(countries => {
                console.log(countries);

                countries.forEach(country => {

                    const option = document.createElement("option");
                    option.value = country.id;
                    option.textContent = country.country
                    select.appendChild(option);
                });
            })
            .catch(err => console.error("Błąd przy pobieraniu krajów:", err));
    };

    listCountries();

});
