const host = 'http://localhost:8080'

buildMatchesPage()

async function buildMatchesPage() {
  const username = findUsername()
  const matches = await fetchAllMatches()
  console.log(matches)
}

function findUsername() {
  const path = window.location.pathname
  const username = path.split('/')[2]
  return username
}

async function fetchAllMatches() {
  const url = `${host}/api/matches`
  const response = await fetch(url)
  const matches = await response.json()
  return matches
}