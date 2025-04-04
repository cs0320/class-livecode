# Taxicab MD5 demo (TN July 2022)

from hashlib import md5

def brute_force_de_anon(digits, max) -> dict[str, str]:
    # We could also write this out to a file, so we only have to
    # compute everything once. 
    table: dict[str, str] = dict()

    for id_int in range(max):
        id_str = str(id_int).zfill(digits)  # pad to 6 digit string
        id_hash = md5(id_str.encode()).hexdigest() # md5 takes bits
        table[id_hash] = id_str
    return table

if __name__ == '__main__':
    table = brute_force_de_anon(7, 9999999)
    print('Deanonymization table built.')

    id_int = 17
    id_str = str(id_int).zfill(7)
    print(f'id_str; {id_str}')
    id_hash = md5(id_str.encode()).hexdigest() 
    print(f'id_hash: {id_hash}')

    # Now, we deanonymize
    print(f'table says: {id_hash} -> {table[id_hash]}')

###########################################################

# How could the anonymizer have defended against this? 

# One way, maybe the best, would be to build a table of truly-random values for every ID, 
# and substitute those for the originals in the published data set. Then, a reader would have
# no way of reconstructing the new values from the full set of original IDs.

###########################################################

# Another (I would argue less good and more labor-intensive) process:
# Introduce an additional piece of random information for every ID, and
# include that in what gets hashed. If these random values (called "salts") don't get
# released, an attacker needs to build a far larger table. If we have 6-digit IDs and 
# 5-digit salts, instead of 10^6 = 1,000,000 rows, the table would need 
# 10^(6+5) = 10^11 = 100,000,000,000 rows, in order to pre-compute the output for all 
# possible salts. 

# If the salts are released too, then we're still in trouble. But at least the 
# attacker can't efficiently *pre-compute* a table before they have the salts.

def salted_anon():
    public = []
    private = {}

    for id_int in range(999999):  # same set of IDs to protect 
        import string, random
        id = str(id_int).zfill(6)  # pad to 6 digit string
        salt = ''.join(random.choices(string.digits, k=5)) 
        id_hash = md5(str(id+salt).encode()).hexdigest()
        public += [id_hash]
        private[id] = salt
    return (public, private)

# Realistically, we'd now remember both the salt and the hashed value.
# We (the Taxi people) have the secret salts, but the attacker doesn't.
























#####  PART 1  #####

# Suppose I have a file full of 6-digit "anonymized" numeric IDs
# which have been "anonymized' using the standard md5 hash function.

# This helper function builds such a file, for use in the live demo.
# For brevity, the function only hashes the first 99 possible IDs, but 
#   as we'll see, that limitation doesn't really matter.
def write_file_of_hashes(how_many: int):
    import sys
    with open('anon.txt', 'w') as file:
        for id_int in range(how_many):
            # id_int holds a *number*, like 17. We want to hash a string padded to
            #   6 digits. To do that, we'll convert the number to a string, and use
            #   a helper function (zfill) to add zeroes as needed.
            id = str(id_int).zfill(6)  
            # Now apply the md5 hash function. Python 3's library is very careful
            #   about some technical details (like which text encoding is being used, etc.)        
            id_hash = md5(id.encode('utf-8')).hexdigest()
            file.write(id_hash)
            # Python helpfully translates line-endings for OSs if a file is opened in text mode.
            # According to the docs: 
            #   "Do not use os.linesep as a line terminator when writing files opened in text mode
            #   (the default); use a single '\n' instead, on all platforms."
            file.write('\n') 





def de_anonymize():    
    table = brute_force_de_anon_6()
    with open('anon.txt', 'r') as file:
        lines = file.read().split()
        for line in lines:
            print(f'anon {line} was originally {table[line]}')

