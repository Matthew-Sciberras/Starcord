export function copyLink(fragment: string) {
  const url = window.location.origin + window.location.pathname;
  const fullUrl = `${url}#${fragment}`;

  navigator.clipboard.writeText(fullUrl).then(() => {
    console.log('Link copied to clipboard:', fullUrl);
  });
}
