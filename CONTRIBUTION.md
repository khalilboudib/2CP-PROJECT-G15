# Contributions to HackINI 2024

First of all, thank you for willing to contribute in making this event a memorable one.  

## Quick start

1. Fork the repository.  

2. Clone the forked repository :  

```bash
git clone https://github.com/${your_username}/HackINI-2K24-CTF.git

3. Configure upstream repository :  

```bash
git remote add upstream https://github.com/Shellmates/HackINI-2K24-CTF.git
```

4. Make sure to fetch from upstream first :  

```bash
git pull upstream main
git push origin main # to update your remote fork
```

5. Make a separate branch :  

```bash
git checkout -b {difficulty}-{category_name}-{challenge_name}
```

6. Make changes locally.  

7. Add the affected files :  

```bash
git add /path/to/challenge_directory
```

8. Commit your changes :  

```bash
git commit
```

9. Push your changes :  

```bash
git push origin {difficulty}-{category_challenge}-{challenge_name}
```

10. Make a pull request on Github.  

### Important note

**Always** pull from upstream before starting to make your local changes :  

```bash
git checkout main
git pull upstream main
git push origin main # to update your remote fork
```