const host = 'http://localhost:8080'

buildMatchesPage()

async function buildMatchesPage() {
  const username = findUsername()
  const matches = await fetchAllMatches()
  const gamblerVotes = await getGamblerVotes(username)

  const notVotedMatches = matches.filter(match => !gamblerVotes.votes[match.matchId])
  
  showMatches(notVotedMatches)
}

async function getGamblerVotes(username) {
  const response = await fetch(`${host}/api/votes/${username}`)
  const gamblerVotes = await response.json()
  return gamblerVotes
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

function showMatches(matches) {
  const matchesDiv = document.getElementById('matches')
  matches.forEach(match => matchesDiv.appendChild(createMatchDiv(match)))
}

function createMatchDiv(match) {
  const matchDiv = document.createElement('div')
  matchDiv.className = 'match card col-12 col-md-5 col-lg-3'
  
  const matchImagesDiv = createMatchImagesDiv(match)
  const matchCardBodyDiv = createMatchCardBodyDiv(match)

  matchDiv.append(matchImagesDiv, matchCardBodyDiv)

  return matchDiv
}

function createMatchImagesDiv(match) {
  const matchImagesDiv = document.createElement('div')
  matchImagesDiv.className = 'match-images'

  const homeTeamImage = createCardTopImage(match.homeTeam.countryCode, match.matchId, 'HOME')
  
  const flagSeparatorDiv = document.createElement('div')
  flagSeparatorDiv.className = 'highlighted-text'
  flagSeparatorDiv.innerHTML = 'vs'
  voteOnClick(flagSeparatorDiv, match.matchId, 'TIE')

  const awayTeamImage = createCardTopImage(match.awayTeam.countryCode, match.matchId, 'AWAY')

  matchImagesDiv.appendChild(homeTeamImage)
  matchImagesDiv.appendChild(flagSeparatorDiv)
  matchImagesDiv.appendChild(awayTeamImage)
  return matchImagesDiv
}

function createCardTopImage(countryCode, matchId, teamType) {
  const teamImage = document.createElement('img')
  teamImage.className = 'card-img-top'
  teamImage.src = flag(countryCode)
  voteOnClick(teamImage, matchId, teamType)
  return teamImage
}

function voteOnClick(htmlElement, matchId, teamType) {
  const requestBody = {
    username: findUsername(),
    vote: teamType,
  }
  const requestParams = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(requestBody)
  }
  htmlElement.onclick = async () => {
    const response = await fetch(`${host}/api/matches/${matchId}/votes`, requestParams)
    if (response.status === 204) {
      const voteDiv = document.querySelector(`#match-${matchId} .${teamType.toLowerCase()}`)
      voteDiv.innerHTML = Number(voteDiv.innerHTML) + 1
    }
  }
}

function flag(countryCode) {
  return `https://cloudinary.fifa.com/api/v3/picture/flags-sq-4/${countryCode}?tx=c_fill,g_auto,q_auto`
}

function createMatchCardBodyDiv(match) {
  const matchCardBodyDiv = document.createElement('div')
  matchCardBodyDiv.id = `match-${match.matchId}`
  matchCardBodyDiv.className = 'card-body row-items'

  const homeTeamVotesDiv = document.createElement('div')
  homeTeamVotesDiv.className = 'vote-count home'
  homeTeamVotesDiv.innerHTML = match.homeVoteCount

  const tieVotesDiv = document.createElement('div')
  tieVotesDiv.className = 'vote-count tie'
  tieVotesDiv.innerHTML = match.tieVoteCount

  const awayTeamVotesDiv = document.createElement('div')
  awayTeamVotesDiv.className = 'vote-count away'
  awayTeamVotesDiv.innerHTML = match.awayVoteCount

  matchCardBodyDiv.append(homeTeamVotesDiv, tieVotesDiv, awayTeamVotesDiv)
  return matchCardBodyDiv
}