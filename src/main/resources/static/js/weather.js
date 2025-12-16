document.addEventListener("DOMContentLoaded", () => {
    const select = document.getElementById("country");
    const temperatureSpan = document.getElementById("temperature");
    const flagImg = document.getElementById("country-flag");

    const setDefaultCountry = () => {
        const defaultCountryId = 141; // Poland
        fetch(`/api/countries/selected`)
            .then(res => {
                if (!res.ok) return null;
                return res.json();
            })
            .then(selectedCountry => {
                if (selectedCountry) {
                    select.value = selectedCountry.id;
                    getTemperature(selectedCountry.country);
                    updateFlag(selectedCountry);
                } else {
                select.value = defaultCountryId;
                const selectedOption = select.options[select.selectedIndex];
                if (selectedOption) getTemperature(selectedOption.textContent);
                }

                const defaultCountry = countries.find(c => c.id === defaultCountryId);
                if(defaultCountry) updateFlag(defaultCountry);

            })
            .catch(err => console.error(err));
    };

    const listCountries = () => {
        fetch(`/api/countries`)
            .then(res => res.json())
            .then(countries => {
                countries.sort((a,b) => a.country.localeCompare(b.country));

                countries.forEach(country => {
                    const option = document.createElement("option");
                    option.value = country.id;
                    option.textContent = country.country;
                    select.appendChild(option);
                });

                setDefaultCountry();

            })
            .catch(err => console.error("Error:", err));
    };

    const getTemperature = (countryName) => {
        fetch(`/api/weather?country=${encodeURIComponent(countryName)}`)
            .then(res => res.json())
            .then(temp => {
                console.log(`Temperature in ${countryName}: ${temp}°C`);
                if (temperatureSpan) {
                    temperatureSpan.textContent = `${temp}°C`;
                }
            })
            .catch(err => console.error(err));
    };

    select.addEventListener("change", () => {
        const selectedId = Number(select.value);
        const selectedOption = select.options[select.selectedIndex];
        const countryName = selectedOption.textContent;


        fetch(`/api/countries/select`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(Number(select.value))
        })
        .then(res => res.json())
        .then(data => {
            console.log("API response", data);
            getTemperature(countryName);
            location.reload();
        })
        .catch(err => console.error(err));
        location.reload();
    });


    const updateFlag = (country) => {
    if(flagImg && country.img) {
        flagImg.src = country.img;
        alert(country.img);
        }
    };




    listCountries();
});
