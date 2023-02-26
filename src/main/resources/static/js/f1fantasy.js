function calculateBestTeam() {
    fetch('/bestTeam')
        .then(response => response.json())
        .then(data => {
          console.log('Best team:', data);
          // Do something with the data here
        })
        .catch(error => {
          console.error('Error:', error);
        });
}